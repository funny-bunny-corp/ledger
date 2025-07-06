import React from 'react';
import { Link, useLocation } from 'react-router-dom';
import { NavBar, NavLink, Container, FlexContainer } from './common/StyledComponents';

const Navigation: React.FC = () => {
  const location = useLocation();

  return (
    <NavBar>
      <Container>
        <FlexContainer justify="space-between" align="center">
          <Link to="/" style={{ textDecoration: 'none' }}>
            <h2 style={{ color: 'white', margin: 0, fontSize: '24px' }}>
              💰 Paymentic Ledger
            </h2>
          </Link>
          <FlexContainer gap="16px">
            <NavLink 
              as={Link} 
              to="/" 
              active={location.pathname === '/'}
            >
              Home
            </NavLink>
            <NavLink 
              as={Link} 
              to="/create-shelf" 
              active={location.pathname === '/create-shelf'}
            >
              Create Shelf
            </NavLink>
          </FlexContainer>
        </FlexContainer>
      </Container>
    </NavBar>
  );
};

export default Navigation;