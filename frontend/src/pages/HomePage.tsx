import React from 'react';
import { Link, useNavigate } from 'react-router-dom';
import { 
  Container, 
  Title, 
  Text, 
  Card, 
  GradientCard, 
  Button, 
  FlexContainer, 
  Grid 
} from '../components/common/StyledComponents';

const HomePage: React.FC = () => {
  const navigate = useNavigate();

  const handleCreateShelf = () => {
    navigate('/create-shelf');
  };

  const handleViewShelves = () => {
    navigate('/shelves');
  };

  return (
    <Container>
      <Title>Paymentic Ledger</Title>
      
      <GradientCard>
        <Text style={{ textAlign: 'center', fontSize: '18px', marginBottom: '24px' }}>
          Welcome to the Double Entry Ledger System. Manage your financial records with shelves, books, and transactions.
        </Text>
        
        <FlexContainer justify="center" gap="16px">
          <Button onClick={handleCreateShelf} variant="primary">
            Create New Shelf
          </Button>
          <Button onClick={handleViewShelves} variant="secondary">
            View All Shelves
          </Button>
        </FlexContainer>
      </GradientCard>

      <Grid columns={3} gap="24px">
        <Card>
          <h3 style={{ marginBottom: '16px', color: '#2C3E50' }}>📚 Shelves</h3>
          <Text>
            Organize your financial data into shelves. Each shelf belongs to an owner and contains multiple books.
          </Text>
          <Button 
            onClick={() => navigate('/shelves')} 
            variant="outline"
            style={{ marginTop: '16px' }}
          >
            Manage Shelves
          </Button>
        </Card>

        <Card>
          <h3 style={{ marginBottom: '16px', color: '#2C3E50' }}>📖 Books</h3>
          <Text>
            Books contain your financial records. Different types of books can represent different account categories.
          </Text>
          <Button 
            onClick={() => navigate('/shelves')} 
            variant="outline"
            style={{ marginTop: '16px' }}
          >
            View Books
          </Button>
        </Card>

        <Card>
          <h3 style={{ marginBottom: '16px', color: '#2C3E50' }}>📝 Records</h3>
          <Text>
            Individual transactions with amounts, currencies, and types (Credit/Debit). Track your financial activities.
          </Text>
          <Button 
            onClick={() => navigate('/shelves')} 
            variant="outline"
            style={{ marginTop: '16px' }}
          >
            View Records
          </Button>
        </Card>
      </Grid>

      <Card style={{ marginTop: '32px' }}>
        <h3 style={{ marginBottom: '16px', color: '#2C3E50' }}>🚀 Getting Started</h3>
        <Text>
          1. Create a new shelf with an owner ID
        </Text>
        <Text>
          2. Browse books within your shelves
        </Text>
        <Text>
          3. View detailed records for each book
        </Text>
        <Text>
          4. Track your financial transactions with ease
        </Text>
      </Card>
    </Container>
  );
};

export default HomePage;