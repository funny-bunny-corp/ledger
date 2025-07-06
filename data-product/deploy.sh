#!/bin/bash

# Payment Service Data Product Deployment Script
# This script deploys the complete data product to Apache Pinot

set -e

# Configuration
PINOT_CONTROLLER_HOST=${PINOT_CONTROLLER_HOST:-"localhost"}
PINOT_CONTROLLER_PORT=${PINOT_CONTROLLER_PORT:-"9000"}
PINOT_CONTROLLER_URL="http://${PINOT_CONTROLLER_HOST}:${PINOT_CONTROLLER_PORT}"

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
NC='\033[0m' # No Color

# Logging functions
log_info() {
    echo -e "${GREEN}[INFO]${NC} $1"
}

log_warn() {
    echo -e "${YELLOW}[WARN]${NC} $1"
}

log_error() {
    echo -e "${RED}[ERROR]${NC} $1"
}

# Function to check if Pinot is accessible
check_pinot_health() {
    log_info "Checking Pinot controller health..."
    if curl -s -f "${PINOT_CONTROLLER_URL}/health" > /dev/null; then
        log_info "Pinot controller is healthy"
        return 0
    else
        log_error "Pinot controller is not accessible at ${PINOT_CONTROLLER_URL}"
        return 1
    fi
}

# Function to create a schema
create_schema() {
    local schema_file=$1
    local schema_name=$(basename "$schema_file" .json)
    
    log_info "Creating schema: ${schema_name}"
    
    if curl -s -X POST "${PINOT_CONTROLLER_URL}/schemas" \
        -H "Content-Type: application/json" \
        -d @"$schema_file" | grep -q "successfully"; then
        log_info "Schema ${schema_name} created successfully"
    else
        log_warn "Schema ${schema_name} might already exist or failed to create"
    fi
}

# Function to create a table
create_table() {
    local table_file=$1
    local table_name=$(basename "$table_file" .json)
    
    log_info "Creating table: ${table_name}"
    
    if curl -s -X POST "${PINOT_CONTROLLER_URL}/tables" \
        -H "Content-Type: application/json" \
        -d @"$table_file" | grep -q "successfully"; then
        log_info "Table ${table_name} created successfully"
    else
        log_warn "Table ${table_name} might already exist or failed to create"
    fi
}

# Function to wait for table to be ready
wait_for_table() {
    local table_name=$1
    local max_attempts=30
    local attempt=0
    
    log_info "Waiting for table ${table_name} to be ready..."
    
    while [ $attempt -lt $max_attempts ]; do
        if curl -s "${PINOT_CONTROLLER_URL}/tables/${table_name}/state" | grep -q "ONLINE"; then
            log_info "Table ${table_name} is online"
            return 0
        fi
        
        attempt=$((attempt + 1))
        sleep 10
    done
    
    log_error "Table ${table_name} did not come online within expected time"
    return 1
}

# Function to validate deployment
validate_deployment() {
    log_info "Validating deployment..."
    
    # Check if schemas exist
    for schema in transaction_wallet_registered transaction_booked payment_analytics; do
        if curl -s "${PINOT_CONTROLLER_URL}/schemas/${schema}" | grep -q "schemaName"; then
            log_info "Schema ${schema} is present"
        else
            log_error "Schema ${schema} is missing"
            return 1
        fi
    done
    
    # Check if tables exist
    for table in transaction_wallet_registered_rt transaction_booked_rt payment_analytics; do
        if curl -s "${PINOT_CONTROLLER_URL}/tables/${table}" | grep -q "tableName"; then
            log_info "Table ${table} is present"
        else
            log_error "Table ${table} is missing"
            return 1
        fi
    done
    
    log_info "Deployment validation successful"
}

# Function to show usage
show_usage() {
    echo "Usage: $0 [OPTIONS]"
    echo "Options:"
    echo "  -h, --help              Show this help message"
    echo "  -c, --controller URL    Pinot controller URL (default: http://localhost:9000)"
    echo "  -v, --validate-only     Only validate existing deployment"
    echo "  -s, --schemas-only      Only deploy schemas"
    echo "  -t, --tables-only       Only deploy tables"
    echo "  --dry-run              Show what would be deployed without actually doing it"
}

# Parse command line arguments
VALIDATE_ONLY=false
SCHEMAS_ONLY=false
TABLES_ONLY=false
DRY_RUN=false

while [[ $# -gt 0 ]]; do
    case $1 in
        -h|--help)
            show_usage
            exit 0
            ;;
        -c|--controller)
            PINOT_CONTROLLER_URL="$2"
            shift 2
            ;;
        -v|--validate-only)
            VALIDATE_ONLY=true
            shift
            ;;
        -s|--schemas-only)
            SCHEMAS_ONLY=true
            shift
            ;;
        -t|--tables-only)
            TABLES_ONLY=true
            shift
            ;;
        --dry-run)
            DRY_RUN=true
            shift
            ;;
        *)
            log_error "Unknown option: $1"
            show_usage
            exit 1
            ;;
    esac
done

# Main deployment function
deploy_data_product() {
    log_info "Starting Payment Service Data Product deployment..."
    log_info "Target Pinot Controller: ${PINOT_CONTROLLER_URL}"
    
    # Check prerequisites
    if ! check_pinot_health; then
        log_error "Pinot controller health check failed"
        exit 1
    fi
    
    # Validate only mode
    if [ "$VALIDATE_ONLY" = true ]; then
        validate_deployment
        exit $?
    fi
    
    # Dry run mode
    if [ "$DRY_RUN" = true ]; then
        log_info "DRY RUN MODE - Would deploy the following:"
        echo "Schemas:"
        ls -1 pinot-schemas/*.json
        echo "Tables:"
        ls -1 pinot-tables/*.json
        exit 0
    fi
    
    # Deploy schemas
    if [ "$TABLES_ONLY" != true ]; then
        log_info "Deploying schemas..."
        
        create_schema "pinot-schemas/transaction-wallet-registered-schema.json"
        create_schema "pinot-schemas/transaction-booked-schema.json"
        create_schema "pinot-schemas/payment-analytics-schema.json"
        
        log_info "Schema deployment completed"
    fi
    
    # Deploy tables
    if [ "$SCHEMAS_ONLY" != true ]; then
        log_info "Deploying tables..."
        
        # Create real-time tables
        create_table "pinot-tables/transaction-wallet-registered-rt-table.json"
        create_table "pinot-tables/transaction-booked-rt-table.json"
        
        # Create offline tables
        create_table "pinot-tables/transaction-wallet-registered-offline-table.json"
        create_table "pinot-tables/transaction-booked-offline-table.json"
        
        # Create analytical view
        create_table "pinot-tables/payment-analytics-view.json"
        
        log_info "Table deployment completed"
        
        # Wait for tables to be ready
        wait_for_table "transaction_wallet_registered_rt"
        wait_for_table "transaction_booked_rt"
        wait_for_table "payment_analytics"
    fi
    
    # Final validation
    if ! validate_deployment; then
        log_error "Deployment validation failed"
        exit 1
    fi
    
    log_info "Payment Service Data Product deployment completed successfully!"
    log_info "You can now start querying the tables:"
    echo "  - transaction_wallet_registered_rt (real-time)"
    echo "  - transaction_wallet_registered_offline (historical)"
    echo "  - transaction_booked_rt (real-time)"
    echo "  - transaction_booked_offline (historical)"
    echo "  - payment_analytics (analytical view)"
}

# Function to clean up deployment
cleanup_deployment() {
    log_warn "Cleaning up Payment Service Data Product deployment..."
    
    # Delete tables
    for table in transaction_wallet_registered_rt transaction_wallet_registered_offline \
                transaction_booked_rt transaction_booked_offline payment_analytics; do
        log_info "Deleting table: ${table}"
        curl -X DELETE "${PINOT_CONTROLLER_URL}/tables/${table}" || true
    done
    
    # Delete schemas
    for schema in transaction_wallet_registered transaction_booked payment_analytics; do
        log_info "Deleting schema: ${schema}"
        curl -X DELETE "${PINOT_CONTROLLER_URL}/schemas/${schema}" || true
    done
    
    log_info "Cleanup completed"
}

# Handle cleanup on script exit
trap cleanup_deployment EXIT

# Check if cleanup is requested
if [ "$1" = "cleanup" ]; then
    cleanup_deployment
    exit 0
fi

# Change to script directory
cd "$(dirname "$0")"

# Check if required files exist
if [ ! -d "pinot-schemas" ] || [ ! -d "pinot-tables" ]; then
    log_error "Required directories not found. Please ensure you're in the correct directory."
    exit 1
fi

# Run deployment
deploy_data_product

log_info "Deployment script completed successfully!"
log_info "For operational commands, see the README.md file"
log_info "For example queries, check the documentation in the README.md"