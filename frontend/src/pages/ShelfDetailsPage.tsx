import React, { useState, useEffect } from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import { apiService, handleApiError } from '../services/api';
import { Shelf, Books } from '../types';
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

const ShelfDetailsPage: React.FC = () => {
  const { id } = useParams<{ id: string }>();
  const navigate = useNavigate();
  const [shelf, setShelf] = useState<Shelf | null>(null);
  const [books, setBooks] = useState<Books>([]);
  const [isLoadingShelf, setIsLoadingShelf] = useState(true);
  const [isLoadingBooks, setIsLoadingBooks] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    if (id) {
      fetchShelf();
      fetchBooks();
    }
  }, [id]);

  const fetchShelf = async () => {
    try {
      setIsLoadingShelf(true);
      setError(null);
      const shelfData = await apiService.getShelf(id!);
      setShelf(shelfData);
    } catch (err) {
      setError(handleApiError(err));
    } finally {
      setIsLoadingShelf(false);
    }
  };

  const fetchBooks = async () => {
    try {
      setIsLoadingBooks(true);
      const booksData = await apiService.getBooks(id!);
      setBooks(booksData);
    } catch (err) {
      console.error('Error fetching books:', err);
      // Don't set error for books as shelf might not have books yet
    } finally {
      setIsLoadingBooks(false);
    }
  };

  const handleBookClick = (bookId: string) => {
    navigate(`/shelves/${id}/books/${bookId}/records`);
  };

  const handleGoBack = () => {
    navigate('/');
  };

  if (isLoadingShelf) {
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
        <Button onClick={handleGoBack} variant="outline">
          Back to Home
        </Button>
      </Container>
    );
  }

  if (!shelf) {
    return (
      <Container>
        <ErrorMessage>Shelf not found</ErrorMessage>
        <Button onClick={handleGoBack} variant="outline">
          Back to Home
        </Button>
      </Container>
    );
  }

  return (
    <Container>
      <FlexContainer justify="space-between" align="center">
        <Title>Shelf Details</Title>
        <Button onClick={handleGoBack} variant="outline">
          Back to Home
        </Button>
      </FlexContainer>

      <Card>
        <Subtitle>Shelf Information</Subtitle>
        <FlexContainer direction="column" gap="8px">
          <Text><strong>ID:</strong> {shelf.id}</Text>
          <Text><strong>Owner ID:</strong> {shelf.owner.id}</Text>
          <Text><strong>Version:</strong> {shelf.version}</Text>
        </FlexContainer>
      </Card>

      <Card>
        <FlexContainer justify="space-between" align="center">
          <Subtitle>Books in this Shelf</Subtitle>
          <Button 
            onClick={() => fetchBooks()} 
            variant="outline"
            disabled={isLoadingBooks}
          >
            {isLoadingBooks ? 'Refreshing...' : 'Refresh Books'}
          </Button>
        </FlexContainer>

        {isLoadingBooks ? (
          <LoadingSpinner />
        ) : books.length === 0 ? (
          <Text style={{ textAlign: 'center', color: '#5D6D7E' }}>
            No books found in this shelf.
          </Text>
        ) : (
          <Table>
            <TableHeader>
              <TableRow>
                <TableHeaderCell>Book ID</TableHeaderCell>
                <TableHeaderCell>Type</TableHeaderCell>
                <TableHeaderCell>Version</TableHeaderCell>
                <TableHeaderCell>Actions</TableHeaderCell>
              </TableRow>
            </TableHeader>
            <tbody>
              {books.map((book) => (
                <TableRow key={book.id}>
                  <TableCell>{book.id}</TableCell>
                  <TableCell>
                    <Badge variant="info">{book.type}</Badge>
                  </TableCell>
                  <TableCell>{book.version}</TableCell>
                  <TableCell>
                    <Button
                      onClick={() => handleBookClick(book.id)}
                      variant="outline"
                      style={{ padding: '4px 8px', fontSize: '12px' }}
                    >
                      View Records
                    </Button>
                  </TableCell>
                </TableRow>
              ))}
            </tbody>
          </Table>
        )}
      </Card>

      <Card>
        <h3 style={{ marginBottom: '16px', color: '#2C3E50' }}>📖 About Books</h3>
        <Text>
          Books are containers for financial records within a shelf. Each book has a specific type 
          (e.g., "payments", "pending") and contains related transactions.
        </Text>
        <Text>
          Click on "View Records" to see detailed transaction history for any book.
        </Text>
      </Card>
    </Container>
  );
};

export default ShelfDetailsPage;