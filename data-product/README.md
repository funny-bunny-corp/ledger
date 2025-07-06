# Payment Service Data Product

## Overview

The Payment Service Data Product is a source-aligned data product following data mesh principles, designed to provide comprehensive analytics and operational insights for the Paymentic payment processing domain. This data product is built on Apache Pinot and provides real-time and historical analysis of payment transactions.

## Domain-Driven Design Context

- **Domain**: Payments
- **Subdomain**: Payment Processing
- **Bounded Context**: Payment Transactions
- **Source System**: Payment Service
- **Event Source**: Kafka topic `payments`

## Data Mesh Principles Applied

### 1. Domain-Oriented Decentralized Data Ownership
- **Data Owner**: Payment Engineering Team
- **Product Owner**: payment-team-lead@paymentic.com
- **Technical Owner**: payment-tech-lead@paymentic.com
- **Source-Aligned**: Directly aligned with payment service domain

### 2. Data as a Product
- **Product Thinking**: Treated as a first-class product with clear SLOs
- **Self-Service**: Standardized APIs and schemas for easy consumption
- **Discoverability**: Rich metadata and business glossary
- **Quality Assurance**: Built-in data quality monitoring

### 3. Self-Serve Data Infrastructure Platform
- **Apache Pinot**: High-performance OLAP database
- **Real-time & Batch**: Supports both streaming and batch processing
- **Auto-scaling**: Configurable tenant-based scaling
- **Multi-tier Storage**: Hot, warm, and cold storage tiers

### 4. Federated Computational Governance
- **Data Contracts**: Schema evolution and SLA guarantees
- **Privacy by Design**: PII fields automatically hashed
- **Access Control**: Role-based access control
- **Compliance**: 7-year data retention for regulatory compliance

## Data Assets

### 1. Transaction Wallet Registered Events
- **Real-time Table**: `transaction_wallet_registered_rt`
- **Offline Table**: `transaction_wallet_registered_offline`
- **Purpose**: Track when transactions are registered in digital wallets
- **Latency**: <1s for real-time, <1h for historical

### 2. Transaction Booked Events
- **Real-time Table**: `transaction_booked_rt`
- **Offline Table**: `transaction_booked_offline`
- **Purpose**: Track when transactions are booked in accounting records
- **Latency**: <1s for real-time, <1h for historical

### 3. Payment Analytics View
- **Analytical Table**: `payment_analytics`
- **Purpose**: Combined view for comprehensive payment analysis
- **Features**: Transaction status, processing time metrics, success rates
- **Update Frequency**: Hourly

## Key Features

### Privacy & Security
- **PII Protection**: Automatic hashing of sensitive fields (emails, names)
- **Access Control**: Team-based access permissions
- **Data Classification**: Restricted data with appropriate handling

### Performance Optimization
- **Inverted Indexes**: Fast lookups on transaction IDs and identifiers
- **Bloom Filters**: Efficient existence checks
- **Range Indexes**: Optimized for time-based and amount queries
- **Star Tree Indexes**: Pre-aggregated metrics for analytical queries

### Data Quality
- **Schema Validation**: CloudEvents specification compliance
- **Data Freshness**: Real-time streaming with <1s latency
- **Completeness**: 99.9% data completeness SLO
- **Accuracy**: 99.99% data accuracy SLO

## Service Level Objectives (SLOs)

| Metric | Target | Measurement |
|--------|--------|-------------|
| Availability | 99.9% | Monthly uptime |
| Real-time Latency | <100ms | P95 query response time |
| Batch Latency | <5min | Data ingestion to availability |
| Data Freshness | <1s | Event to query-able |
| Accuracy | 99.99% | Data validation checks |

## Getting Started

### Prerequisites
- Apache Pinot cluster
- Kafka cluster with `payments` topic
- S3 bucket for data storage
- Appropriate access credentials

### Deployment

1. **Create Schemas**
   ```bash
   # Create schemas
   curl -X POST "http://pinot-controller:9000/schemas" \
     -H "Content-Type: application/json" \
     -d @pinot-schemas/transaction-wallet-registered-schema.json
   
   curl -X POST "http://pinot-controller:9000/schemas" \
     -H "Content-Type: application/json" \
     -d @pinot-schemas/transaction-booked-schema.json
   
   curl -X POST "http://pinot-controller:9000/schemas" \
     -H "Content-Type: application/json" \
     -d @pinot-schemas/payment-analytics-schema.json
   ```

2. **Create Tables**
   ```bash
   # Create real-time tables
   curl -X POST "http://pinot-controller:9000/tables" \
     -H "Content-Type: application/json" \
     -d @pinot-tables/transaction-wallet-registered-rt-table.json
   
   curl -X POST "http://pinot-controller:9000/tables" \
     -H "Content-Type: application/json" \
     -d @pinot-tables/transaction-booked-rt-table.json
   
   # Create offline tables
   curl -X POST "http://pinot-controller:9000/tables" \
     -H "Content-Type: application/json" \
     -d @pinot-tables/transaction-wallet-registered-offline-table.json
   
   curl -X POST "http://pinot-controller:9000/tables" \
     -H "Content-Type: application/json" \
     -d @pinot-tables/transaction-booked-offline-table.json
   
   # Create analytical view
   curl -X POST "http://pinot-controller:9000/tables" \
     -H "Content-Type: application/json" \
     -d @pinot-tables/payment-analytics-view.json
   ```

### Example Queries

#### Real-time Transaction Volume
```sql
SELECT 
    transactionType,
    currency,
    COUNT(*) as transaction_count,
    SUM(amount) as total_amount,
    AVG(amount) as avg_amount
FROM transaction_wallet_registered_rt
WHERE registeredAt > ago('PT1H')
GROUP BY transactionType, currency
ORDER BY total_amount DESC
```

#### Payment Success Rate
```sql
SELECT 
    transactionType,
    currency,
    COUNT(*) as total_transactions,
    SUM(CASE WHEN bookingStatus = 'SUCCESS' THEN 1 ELSE 0 END) as successful_transactions,
    (SUM(CASE WHEN bookingStatus = 'SUCCESS' THEN 1 ELSE 0 END) * 100.0 / COUNT(*)) as success_rate
FROM transaction_booked_rt
WHERE bookedAt > ago('PT24H')
GROUP BY transactionType, currency
ORDER BY success_rate DESC
```

#### Transaction Processing Time Analysis
```sql
SELECT 
    transactionType,
    currency,
    AVG(processingTimeMs) as avg_processing_time,
    PERCENTILE(processingTimeMs, 50) as p50_processing_time,
    PERCENTILE(processingTimeMs, 95) as p95_processing_time,
    PERCENTILE(processingTimeMs, 99) as p99_processing_time
FROM payment_analytics
WHERE transactionTime > ago('PT24H')
    AND processingTimeMs IS NOT NULL
GROUP BY transactionType, currency
ORDER BY avg_processing_time DESC
```

#### Daily Transaction Trends
```sql
SELECT 
    DATE_TRUNC('day', transactionTime) as transaction_date,
    transactionType,
    COUNT(*) as transaction_count,
    SUM(amount) as total_amount
FROM payment_analytics
WHERE transactionTime > ago('P30D')
GROUP BY DATE_TRUNC('day', transactionTime), transactionType
ORDER BY transaction_date DESC, total_amount DESC
```

## Data Governance

### Data Retention
- **Real-time Tables**: 7 days
- **Offline Tables**: 7 years
- **Analytical Views**: 7 years

### Privacy Controls
- **PII Fields**: Automatically hashed using SHA256
- **Data Anonymization**: Hash-based anonymization
- **Access Logging**: All data access is logged

### Compliance
- **GDPR**: Right to be forgotten implemented via data deletion APIs
- **Financial Regulations**: 7-year retention for audit compliance
- **Data Lineage**: Complete lineage from source events to analytics

## Monitoring & Alerting

### Key Metrics
- **Ingestion Rate**: Events per second
- **Query Performance**: P95 latency
- **Data Freshness**: Time from event to query-able
- **Error Rate**: Failed ingestion percentage

### Alerts
- **Data Freshness**: Alert if data is >5 minutes old
- **Query Performance**: Alert if P95 latency >500ms
- **Ingestion Errors**: Alert if error rate >1%
- **Schema Evolution**: Alert on breaking changes

## Support & Contact

- **Team**: Payment Engineering Team
- **Product Owner**: payment-team-lead@paymentic.com
- **Technical Owner**: payment-tech-lead@paymentic.com
- **Documentation**: See `/docs` folder for detailed guides
- **Issue Tracking**: Internal payment team issue tracker

## Contributing

This data product follows the payment service development lifecycle:

1. **Schema Changes**: Must be backward compatible
2. **Performance**: All changes must maintain SLOs
3. **Testing**: Comprehensive testing in staging environment
4. **Documentation**: All changes must be documented
5. **Monitoring**: New metrics and alerts as needed

## Related Documentation

- [AsyncAPI Specification](../src/main/resources/asyncapi/asyncapi.yaml)
- [Domain Model Documentation](../src/main/java/com/paymentic/domain/)
- [Data Mesh Architecture Guide](./docs/data-mesh-architecture.md)
- [Operational Runbook](./docs/operational-runbook.md)
- [Query Examples](./docs/query-examples.md)