import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { apiService, handleApiError } from '../services/api';
import { 
  Container, 
  Title, 
  Card, 
  Button, 
  Input, 
  Label, 
  FlexContainer, 
  ErrorMessage,
  SuccessMessage,
  LoadingSpinner
} from '../components/common/StyledComponents';

const CreateShelfPage: React.FC = () => {
  const [ownerId, setOwnerId] = useState('');
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState<string | null>(null);
  const [success, setSuccess] = useState<string | null>(null);
  const navigate = useNavigate();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    
    if (!ownerId.trim()) {
      setError('Owner ID is required');
      return;
    }

    setIsLoading(true);
    setError(null);
    setSuccess(null);

    try {
      const result = await apiService.createShelf({
        owner: {
          id: ownerId.trim()
        }
      });

      setSuccess(`Shelf created successfully! ID: ${result.id}`);
      
      // Navigate to the shelf details page after a short delay
      setTimeout(() => {
        navigate(`/shelves/${result.id}`);
      }, 2000);
      
    } catch (err) {
      setError(handleApiError(err));
    } finally {
      setIsLoading(false);
    }
  };

  const handleGoBack = () => {
    navigate('/');
  };

  return (
    <Container>
      <Title>Create New Shelf</Title>
      
      <Card style={{ maxWidth: '500px', margin: '0 auto' }}>
        <form onSubmit={handleSubmit}>
          <div style={{ marginBottom: '24px' }}>
            <Label htmlFor="ownerId">Owner ID</Label>
            <Input
              id="ownerId"
              type="text"
              value={ownerId}
              onChange={(e) => setOwnerId(e.target.value)}
              placeholder="Enter owner ID (e.g., 99eabea1-8dbd-437f-a706-a73162d5ba7b)"
              disabled={isLoading}
            />
          </div>

          {error && <ErrorMessage>{error}</ErrorMessage>}
          {success && <SuccessMessage>{success}</SuccessMessage>}

          <FlexContainer gap="16px" justify="space-between">
            <Button 
              type="button" 
              variant="outline" 
              onClick={handleGoBack}
              disabled={isLoading}
            >
              Back to Home
            </Button>
            <Button 
              type="submit" 
              variant="primary"
              disabled={isLoading || !ownerId.trim()}
            >
              {isLoading ? 'Creating...' : 'Create Shelf'}
            </Button>
          </FlexContainer>
        </form>

        {isLoading && <LoadingSpinner />}
      </Card>

      <Card style={{ marginTop: '32px' }}>
        <h3 style={{ marginBottom: '16px', color: '#2C3E50' }}>📋 Instructions</h3>
        <p style={{ color: '#5D6D7E', marginBottom: '8px' }}>
          • The Owner ID should be a valid UUID format
        </p>
        <p style={{ color: '#5D6D7E', marginBottom: '8px' }}>
          • Each shelf belongs to one owner
        </p>
        <p style={{ color: '#5D6D7E', marginBottom: '8px' }}>
          • After creation, you'll be redirected to the shelf details page
        </p>
        <p style={{ color: '#5D6D7E' }}>
          • From there, you can view books and records associated with the shelf
        </p>
      </Card>
    </Container>
  );
};

export default CreateShelfPage;