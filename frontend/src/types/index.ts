// API Response Types
export interface Shelf {
  id: string;
  owner: {
    id: string;
  };
  version: number;
}

export interface ShelfCreated {
  id: string;
}

export interface RequestShelfCreation {
  owner: {
    id: string;
  };
}

export interface Book {
  id: string;
  type: string;
  version: number;
}

export type Books = Book[];

export interface Record {
  id: string;
  at: string; // date string
  amount: number;
  currency: string;
  type: 'CREDIT' | 'DEBIT';
}

export type Records = Record[];

// Error Types
export interface ErrorResponse {
  code: string;
  description: string;
}

export interface Error401 extends ErrorResponse {}
export interface Error403 extends ErrorResponse {}
export interface Error404 extends ErrorResponse {}
export interface Error422 extends ErrorResponse {}
export interface Error500 extends ErrorResponse {}

// API Response Wrappers
export interface ApiResponse<T> {
  data: T;
  status: number;
}

export interface ApiError {
  error: ErrorResponse;
  status: number;
}

// UI State Types
export interface LoadingState {
  isLoading: boolean;
  error: string | null;
}

export interface ShelfState extends LoadingState {
  shelf: Shelf | null;
}

export interface BooksState extends LoadingState {
  books: Books;
}

export interface RecordsState extends LoadingState {
  records: Records;
}