# Paymentic Ledger Frontend

A React.js frontend application for the Paymentic Double Entry Ledger system, built with TypeScript and featuring a beautiful light blue and light purple theme.

## Features

- 🎨 **Beautiful UI**: Clean, modern design with light blue and light purple color scheme
- 📱 **Responsive Design**: Works on desktop, tablet, and mobile devices
- 🔒 **Type Safety**: Built with TypeScript for better development experience
- 🛡️ **Error Handling**: Comprehensive error handling and user feedback
- 🚀 **Performance**: Optimized for fast loading and smooth interactions
- ♿ **Accessibility**: WCAG compliant with proper focus management

## Tech Stack

- **React 18** - Modern React with hooks
- **TypeScript** - Type-safe JavaScript
- **React Router** - Client-side routing
- **Styled Components** - CSS-in-JS styling
- **Axios** - HTTP client for API calls

## Project Structure

```
src/
├── components/
│   ├── common/
│   │   └── StyledComponents.ts    # Reusable styled components
│   └── Navigation.tsx             # Navigation component
├── pages/
│   ├── HomePage.tsx               # Landing page
│   ├── CreateShelfPage.tsx        # Create new shelf
│   ├── ShelfDetailsPage.tsx       # Shelf details and books
│   └── RecordsPage.tsx            # Transaction records
├── services/
│   └── api.ts                     # API service functions
├── styles/
│   └── theme.ts                   # Design system theme
├── types/
│   └── index.ts                   # TypeScript type definitions
└── App.tsx                        # Main application component
```

## API Integration

The frontend integrates with the following OpenAPI endpoints:

- `POST /shelves` - Create a new shelf
- `GET /shelves/{id}` - Get shelf details
- `GET /shelves/{id}/books` - Get books in a shelf
- `GET /shelves/{id}/books/{bookId}/records` - Get transaction records

## Getting Started

### Prerequisites

- Node.js (v14 or higher)
- npm or yarn

### Installation

1. Navigate to the frontend directory:
   ```bash
   cd frontend
   ```

2. Install dependencies:
   ```bash
   npm install
   ```

3. Create environment file:
   ```bash
   cp .env.example .env
   ```

4. Update the `.env` file with your API configuration:
   ```
   REACT_APP_API_BASE_URL=http://localhost:8080
   ```

### Development

Start the development server:
```bash
npm start
```

The application will open at `http://localhost:3000`.

### Building for Production

Build the application:
```bash
npm run build
```

The build files will be in the `build/` directory.

## Color Palette

The application uses a carefully chosen color palette:

- **Primary Blue**: 
  - Light: `#B8D4FF`
  - Main: `#87CEEB` 
  - Dark: `#4682B4`

- **Secondary Purple**:
  - Light: `#E6D6FF`
  - Main: `#D8BFD8`
  - Dark: `#9370DB`

- **Background**:
  - Default: `#F8FAFF`
  - Paper: `#FFFFFF`
  - Card: `#F5F3FF`

## Component Library

The application includes a comprehensive set of styled components:

- **Layout**: `Container`, `FlexContainer`, `Grid`
- **Cards**: `Card`, `GradientCard`
- **Buttons**: `Button` with variants (primary, secondary, outline)
- **Typography**: `Title`, `Subtitle`, `Text`, `Label`
- **Forms**: `Input` with focus states
- **Tables**: `Table`, `TableRow`, `TableCell`, `TableHeader`
- **Feedback**: `LoadingSpinner`, `ErrorMessage`, `SuccessMessage`
- **Navigation**: `NavBar`, `NavLink`
- **Badges**: `Badge` with variants (success, error, warning, info)

## User Flow

1. **Home Page**: Overview of the system with navigation options
2. **Create Shelf**: Form to create a new shelf with owner ID
3. **Shelf Details**: Display shelf information and associated books
4. **Transaction Records**: View detailed transaction history for books

## Features

### Home Page
- Welcome message with system overview
- Quick access to create shelf and view shelves
- Feature cards explaining shelves, books, and records
- Getting started guide

### Create Shelf
- Form validation for owner ID
- Success/error feedback
- Automatic navigation to shelf details after creation
- Instructions and guidance

### Shelf Details
- Shelf information display
- Books table with type and version
- Navigation to transaction records
- Refresh functionality

### Transaction Records
- Financial summary with totals and balance
- Transaction history table
- Color-coded credit/debit indicators
- Date and currency formatting
- Breadcrumb navigation

## Accessibility

The application includes:
- Proper semantic HTML
- ARIA labels and roles
- Keyboard navigation support
- Focus management
- High contrast mode support
- Reduced motion preferences

## Performance Optimizations

- Code splitting with React.lazy
- Memoized components where appropriate
- Optimized images and assets
- Efficient state management
- Minimal re-renders

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## Contributing

1. Follow the existing code style
2. Use TypeScript for type safety
3. Add proper error handling
4. Include responsive design
5. Test on multiple devices
6. Follow accessibility guidelines

## Scripts

- `npm start` - Development server
- `npm run build` - Production build
- `npm test` - Run tests
- `npm run eject` - Eject from Create React App

## License

This project is licensed under the MIT License.

---

Built with ❤️ using React, TypeScript, and modern web technologies.
