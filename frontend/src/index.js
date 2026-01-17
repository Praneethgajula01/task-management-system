import React from 'react';
import ReactDOM from 'react-dom/client';
import './index.css';
import App from './App';

/**
 * React Entry Point
 * 
 * ReactDOM.createRoot: Creates a root for React 18+
 * render(): Renders the App component into the DOM
 */
const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>
);

