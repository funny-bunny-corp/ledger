import styled from 'styled-components';
import { theme } from '../../styles/theme';

export const Container = styled.div`
  max-width: 1200px;
  margin: 0 auto;
  padding: 0 ${theme.spacing.md};
`;

export const Card = styled.div`
  background: ${theme.colors.background.paper};
  border-radius: ${theme.borderRadius.lg};
  box-shadow: ${theme.shadows.md};
  padding: ${theme.spacing.lg};
  margin-bottom: ${theme.spacing.md};
  border: 1px solid ${theme.colors.border.light};
`;

export const GradientCard = styled(Card)`
  background: linear-gradient(135deg, ${theme.colors.primary.light} 0%, ${theme.colors.secondary.light} 100%);
  color: ${theme.colors.text.primary};
`;

export const Button = styled.button<{ variant?: 'primary' | 'secondary' | 'outline' }>`
  padding: ${theme.spacing.sm} ${theme.spacing.md};
  border-radius: ${theme.borderRadius.md};
  font-size: ${theme.typography.fontSize.sm};
  font-weight: ${theme.typography.fontWeight.medium};
  cursor: pointer;
  transition: all 0.2s ease;
  border: none;
  outline: none;

  ${({ variant = 'primary' }) => {
    switch (variant) {
      case 'primary':
        return `
          background: linear-gradient(135deg, ${theme.colors.primary.main} 0%, ${theme.colors.primary.dark} 100%);
          color: white;
          box-shadow: ${theme.shadows.sm};
          
          &:hover {
            box-shadow: ${theme.shadows.md};
            transform: translateY(-1px);
          }
        `;
      case 'secondary':
        return `
          background: linear-gradient(135deg, ${theme.colors.secondary.main} 0%, ${theme.colors.secondary.dark} 100%);
          color: white;
          box-shadow: ${theme.shadows.sm};
          
          &:hover {
            box-shadow: ${theme.shadows.md};
            transform: translateY(-1px);
          }
        `;
      case 'outline':
        return `
          background: transparent;
          color: ${theme.colors.primary.dark};
          border: 2px solid ${theme.colors.primary.main};
          
          &:hover {
            background: ${theme.colors.primary.light};
            color: ${theme.colors.primary.dark};
          }
        `;
    }
  }}

  &:disabled {
    opacity: 0.6;
    cursor: not-allowed;
    transform: none;
  }
`;

export const Title = styled.h1`
  font-size: ${theme.typography.fontSize.xxl};
  font-weight: ${theme.typography.fontWeight.bold};
  color: ${theme.colors.text.primary};
  margin-bottom: ${theme.spacing.lg};
  text-align: center;
  background: linear-gradient(135deg, ${theme.colors.primary.dark} 0%, ${theme.colors.secondary.dark} 100%);
  -webkit-background-clip: text;
  -webkit-text-fill-color: transparent;
  background-clip: text;
`;

export const Subtitle = styled.h2`
  font-size: ${theme.typography.fontSize.xl};
  font-weight: ${theme.typography.fontWeight.semibold};
  color: ${theme.colors.text.primary};
  margin-bottom: ${theme.spacing.md};
`;

export const Text = styled.p`
  font-size: ${theme.typography.fontSize.md};
  color: ${theme.colors.text.secondary};
  line-height: ${theme.typography.lineHeight.normal};
  margin-bottom: ${theme.spacing.sm};
`;

export const Label = styled.label`
  font-size: ${theme.typography.fontSize.sm};
  font-weight: ${theme.typography.fontWeight.medium};
  color: ${theme.colors.text.primary};
  display: block;
  margin-bottom: ${theme.spacing.xs};
`;

export const Input = styled.input`
  width: 100%;
  padding: ${theme.spacing.sm} ${theme.spacing.md};
  border: 2px solid ${theme.colors.border.light};
  border-radius: ${theme.borderRadius.md};
  font-size: ${theme.typography.fontSize.md};
  transition: border-color 0.2s ease;
  background: ${theme.colors.background.paper};

  &:focus {
    outline: none;
    border-color: ${theme.colors.primary.main};
    box-shadow: 0 0 0 3px ${theme.colors.primary.light}40;
  }

  &::placeholder {
    color: ${theme.colors.text.disabled};
  }
`;

export const Table = styled.table`
  width: 100%;
  border-collapse: collapse;
  background: ${theme.colors.background.paper};
  border-radius: ${theme.borderRadius.lg};
  overflow: hidden;
  box-shadow: ${theme.shadows.sm};
`;

export const TableHeader = styled.thead`
  background: linear-gradient(135deg, ${theme.colors.primary.light} 0%, ${theme.colors.secondary.light} 100%);
`;

export const TableRow = styled.tr`
  &:nth-child(even) {
    background: ${theme.colors.background.card};
  }
  
  &:hover {
    background: ${theme.colors.primary.light}20;
    cursor: pointer;
  }
`;

export const TableCell = styled.td`
  padding: ${theme.spacing.sm} ${theme.spacing.md};
  border-bottom: 1px solid ${theme.colors.border.light};
  font-size: ${theme.typography.fontSize.sm};
  color: ${theme.colors.text.primary};
`;

export const TableHeaderCell = styled.th`
  padding: ${theme.spacing.md};
  text-align: left;
  font-weight: ${theme.typography.fontWeight.semibold};
  color: ${theme.colors.text.primary};
  font-size: ${theme.typography.fontSize.sm};
`;

export const LoadingSpinner = styled.div`
  width: 40px;
  height: 40px;
  border: 4px solid ${theme.colors.border.light};
  border-top: 4px solid ${theme.colors.primary.main};
  border-radius: 50%;
  animation: spin 1s linear infinite;
  margin: ${theme.spacing.lg} auto;

  @keyframes spin {
    0% { transform: rotate(0deg); }
    100% { transform: rotate(360deg); }
  }
`;

export const ErrorMessage = styled.div`
  background: ${theme.colors.error}20;
  color: ${theme.colors.error};
  padding: ${theme.spacing.md};
  border-radius: ${theme.borderRadius.md};
  border: 1px solid ${theme.colors.error}40;
  margin-bottom: ${theme.spacing.md};
  text-align: center;
`;

export const SuccessMessage = styled.div`
  background: ${theme.colors.success}20;
  color: ${theme.colors.success};
  padding: ${theme.spacing.md};
  border-radius: ${theme.borderRadius.md};
  border: 1px solid ${theme.colors.success}40;
  margin-bottom: ${theme.spacing.md};
  text-align: center;
`;

export const Badge = styled.span<{ variant?: 'success' | 'error' | 'warning' | 'info' }>`
  padding: ${theme.spacing.xs} ${theme.spacing.sm};
  border-radius: ${theme.borderRadius.sm};
  font-size: ${theme.typography.fontSize.xs};
  font-weight: ${theme.typography.fontWeight.medium};
  
  ${({ variant = 'info' }) => {
    switch (variant) {
      case 'success':
        return `
          background: ${theme.colors.success}20;
          color: ${theme.colors.success};
        `;
      case 'error':
        return `
          background: ${theme.colors.error}20;
          color: ${theme.colors.error};
        `;
      case 'warning':
        return `
          background: ${theme.colors.warning}20;
          color: ${theme.colors.warning};
        `;
      case 'info':
        return `
          background: ${theme.colors.info}20;
          color: ${theme.colors.info};
        `;
    }
  }}
`;

export const FlexContainer = styled.div<{ direction?: 'row' | 'column'; gap?: string; justify?: string; align?: string }>`
  display: flex;
  flex-direction: ${({ direction = 'row' }) => direction};
  gap: ${({ gap = theme.spacing.md }) => gap};
  justify-content: ${({ justify = 'flex-start' }) => justify};
  align-items: ${({ align = 'stretch' }) => align};
`;

export const Grid = styled.div<{ columns?: number; gap?: string }>`
  display: grid;
  grid-template-columns: repeat(${({ columns = 1 }) => columns}, 1fr);
  gap: ${({ gap = theme.spacing.md }) => gap};
`;

export const NavBar = styled.nav`
  background: linear-gradient(135deg, ${theme.colors.primary.main} 0%, ${theme.colors.secondary.main} 100%);
  padding: ${theme.spacing.md} 0;
  box-shadow: ${theme.shadows.md};
  margin-bottom: ${theme.spacing.lg};
`;

export const NavLink = styled.a<{ active?: boolean }>`
  color: white;
  text-decoration: none;
  padding: ${theme.spacing.sm} ${theme.spacing.md};
  border-radius: ${theme.borderRadius.md};
  font-weight: ${theme.typography.fontWeight.medium};
  transition: background-color 0.2s ease;
  
  ${({ active }) => active && `
    background: rgba(255, 255, 255, 0.2);
  `}
  
  &:hover {
    background: rgba(255, 255, 255, 0.1);
  }
`;