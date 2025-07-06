import React from 'react';
import { render, screen } from '@testing-library/react';
import App from './App';

test('renders Paymentic Ledger', () => {
  render(<App />);
  const titleElement = screen.getByText(/paymentic ledger/i);
  expect(titleElement).toBeInTheDocument();
});
