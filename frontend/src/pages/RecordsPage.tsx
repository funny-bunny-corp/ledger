import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { apiService, handleApiError } from '../services/api';
import { Records } from '../types';
import { 
  Container, 
  Title, 
  Subtitle,
  Card, 
  Button, 
  FlexContainer, 
  ErrorMessage,
  LoadingSpinner,
  Table,
  TableHeader,
  TableRow,
  TableCell,
  TableHeaderCell,
  Badge,
  Text
} from '../components/common/StyledComponents';

const RecordsPage: React.FC = () => {
  const { shelfId, bookId } = useParams<{ shelfId: string; bookId: string }>();
  const navigate = useNavigate();
  const [records, setRecords] = useState<Records>([]);
  const [isLoading, setIsLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (shelfId && bookId) {
      fetchRecords();
    }
  }, [shelfId, bookId]);

  const fetchRecords = async () => {
    try {
      setIsLoading(true);
      setError(null);
      const recordsData = await apiService.getRecords(shelfId!, bookId!);
      setRecords(recordsData);
    } catch (err) {
      setError(handleApiError(err));
    } finally {
      setIsLoading(false);
    }
  };

  const handleGoBack = () => {
    navigate(`/shelves/${shelfId}`);
  };

  const handleGoHome = () => {
    navigate('/');
  };

  const formatDate = (dateString: string) => {
    const date = new Date(dateString);
    return date.toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'short',
      day: 'numeric'
    });
  };

  const formatAmount = (amount: number, currency: string) => {
    return new Intl.NumberFormat('en-US', {
      style: 'currency',
      currency: currency
    }).format(amount);
  };

  const calculateTotals = () => {
    let totalCredit = 0;
    let totalDebit = 0;

    records.forEach(record => {
      if (record.type === 'CREDIT') {
        totalCredit += record.amount;
      } else {
        totalDebit += record.amount;
      }
    });

    return { totalCredit, totalDebit, balance: totalCredit - totalDebit };
  };

  const { totalCredit, totalDebit, balance } = calculateTotals();

  if (isLoading) {
    return (
      <Container>
        <LoadingSpinner />
      </Container>
    );
  }

  if (error) {
    return (
      <Container>
        <ErrorMessage>{error}</ErrorMessage>
        <FlexContainer gap="16px">
          <Button onClick={handleGoBack} variant="outline">
            Back to Shelf
          </Button>
          <Button onClick={handleGoHome} variant="outline">
            Back to Home
          </Button>
        </FlexContainer>
      </Container>
    );
  }

  return (
    <Container>
      <FlexContainer justify="space-between" align="center">
        <Title>Transaction Records</Title>
        <FlexContainer gap="16px">
          <Button onClick={handleGoBack} variant="outline">
            Back to Shelf
          </Button>
          <Button onClick={handleGoHome} variant="outline">
            Home
          </Button>
        </FlexContainer>
      </FlexContainer>

      <Card>
        <Subtitle>Book Information</Subtitle>
        <FlexContainer direction="column" gap="8px">
          <Text><strong>Shelf ID:</strong> {shelfId}</Text>
          <Text><strong>Book ID:</strong> {bookId}</Text>
          <Text><strong>Total Records:</strong> {records.length}</Text>
        </FlexContainer>
      </Card>

      {records.length > 0 && (
        <Card>
          <Subtitle>Financial Summary</Subtitle>
          <FlexContainer gap="32px">
            <div>
              <Text><strong>Total Credits:</strong></Text>
              <Text style={{ color: '#10B981', fontSize: '18px', fontWeight: 'bold' }}>
                {formatAmount(totalCredit, records[0]?.currency || 'USD')}
              </Text>
            </div>
            <div>
              <Text><strong>Total Debits:</strong></Text>
              <Text style={{ color: '#EF4444', fontSize: '18px', fontWeight: 'bold' }}>
                {formatAmount(totalDebit, records[0]?.currency || 'USD')}
              </Text>
            </div>
            <div>
              <Text><strong>Balance:</strong></Text>
              <Text style={{ 
                color: balance >= 0 ? '#10B981' : '#EF4444', 
                fontSize: '18px', 
                fontWeight: 'bold' 
              }}>
                {formatAmount(balance, records[0]?.currency || 'USD')}
              </Text>
            </div>
          </FlexContainer>
        </Card>
      )}

      <Card>
        <FlexContainer justify="space-between" align="center">
          <Subtitle>Transaction History</Subtitle>
          <Button 
            onClick={() => fetchRecords()} 
            variant="outline"
            disabled={isLoading}
          >
            {isLoading ? 'Refreshing...' : 'Refresh Records'}
          </Button>
        </FlexContainer>

        {records.length === 0 ? (
          <Text style={{ textAlign: 'center', color: '#5D6D7E' }}>
            No transaction records found for this book.
          </Text>
        ) : (
          <Table>
            <TableHeader>
              <TableRow>
                <TableHeaderCell>Date</TableHeaderCell>
                <TableHeaderCell>Type</TableHeaderCell>
                <TableHeaderCell>Amount</TableHeaderCell>
                <TableHeaderCell>Currency</TableHeaderCell>
                <TableHeaderCell>Transaction ID</TableHeaderCell>
              </TableRow>
            </TableHeader>
            <tbody>
              {records.map((record) => (
                <TableRow key={record.id}>
                  <TableCell>{formatDate(record.at)}</TableCell>
                  <TableCell>
                    <Badge variant={record.type === 'CREDIT' ? 'success' : 'error'}>
                      {record.type}
                    </Badge>
                  </TableCell>
                  <TableCell style={{ 
                    color: record.type === 'CREDIT' ? '#10B981' : '#EF4444',
                    fontWeight: 'bold'
                  }}>
                    {record.type === 'CREDIT' ? '+' : '-'}{formatAmount(record.amount, record.currency)}
                  </TableCell>
                  <TableCell>
                    <Badge variant="info">{record.currency}</Badge>
                  </TableCell>
                  <TableCell style={{ fontSize: '12px', color: '#5D6D7E' }}>
                    {record.id}
                  </TableCell>
                </TableRow>
              ))}
            </tbody>
          </Table>
        )}
      </Card>

      <Card>
        <h3 style={{ marginBottom: '16px', color: '#2C3E50' }}>📊 About Records</h3>
        <Text>
          Records represent individual financial transactions within a book. Each record contains:
        </Text>
        <Text>• <strong>Date:</strong> When the transaction occurred</Text>
        <Text>• <strong>Type:</strong> CREDIT (money in) or DEBIT (money out)</Text>
        <Text>• <strong>Amount:</strong> The transaction value</Text>
        <Text>• <strong>Currency:</strong> The currency used (e.g., USD, EUR, BRL)</Text>
        <Text>• <strong>ID:</strong> Unique identifier for the transaction</Text>
      </Card>
    </Container>
  );
};

export default RecordsPage;