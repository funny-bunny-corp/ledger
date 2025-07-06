import axios, { AxiosResponse } from 'axios';
import { 
  Shelf, 
  ShelfCreated, 
  RequestShelfCreation, 
  Books, 
  Records, 
  ErrorResponse 
} from '../types';

// Base API configuration
const API_BASE_URL = process.env.REACT_APP_API_BASE_URL || 'http://localhost:8080';

const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Error handling interceptor
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.data) {
      return Promise.reject(error.response.data);
    }
    return Promise.reject(error);
  }
);

// API Service Functions
export const apiService = {
  // Shelf operations
  async createShelf(shelfData: RequestShelfCreation): Promise<ShelfCreated> {
    try {
      const response: AxiosResponse<ShelfCreated> = await api.post('/shelves', shelfData);
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  async getShelf(id: string): Promise<Shelf> {
    try {
      const response: AxiosResponse<Shelf> = await api.get(`/shelves/${id}`);
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  // Book operations
  async getBooks(shelfId: string): Promise<Books> {
    try {
      const response: AxiosResponse<Books> = await api.get(`/shelves/${shelfId}/books`);
      return response.data;
    } catch (error) {
      throw error;
    }
  },

  // Record operations
  async getRecords(shelfId: string, bookId: string): Promise<Records> {
    try {
      const response: AxiosResponse<Records> = await api.get(`/shelves/${shelfId}/books/${bookId}/records`);
      return response.data;
    } catch (error) {
      throw error;
    }
  },
};

// Error handling utility
export const handleApiError = (error: any): string => {
  if (error?.code && error?.description) {
    return `${error.code}: ${error.description}`;
  }
  if (error?.message) {
    return error.message;
  }
  return 'An unexpected error occurred';
};