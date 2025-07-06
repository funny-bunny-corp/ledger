import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import Navigation from './components/Navigation';
import HomePage from './pages/HomePage';
import CreateShelfPage from './pages/CreateShelfPage';
import ShelfDetailsPage from './pages/ShelfDetailsPage';
import RecordsPage from './pages/RecordsPage';
import { theme } from './styles/theme';

function App() {
  return (
    <Router>
      <div style={{ 
        backgroundColor: theme.colors.background.default, 
        minHeight: '100vh',
        fontFamily: 'Arial, sans-serif'
      }}>
        <Navigation />
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/create-shelf" element={<CreateShelfPage />} />
          <Route path="/shelves/:id" element={<ShelfDetailsPage />} />
          <Route path="/shelves/:shelfId/books/:bookId/records" element={<RecordsPage />} />
        </Routes>
      </div>
    </Router>
  );
}

export default App;
