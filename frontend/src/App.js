import React, { useState, useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import Login from './components/Login';
import Register from './components/Register';
import TaskList from './components/TaskList';
import './App.css';

/**
 * Main App Component
 * 
 * React Router: Handles navigation between pages
 * - BrowserRouter: Enables routing
 * - Routes: Container for route definitions
 * - Route: Defines a route (path + component)
 * - Navigate: Redirects to another route
 * 
 * useState: Manages component state (data that changes)
 * useEffect: Runs code after component renders (like on page load)
 */
function App() {
  const [isAuthenticated, setIsAuthenticated] = useState(false);
  const [loading, setLoading] = useState(true);

  /**
   * Check if user is authenticated on app load
   * Reads token from localStorage
   */
  useEffect(() => {
    const token = localStorage.getItem('token');
    setIsAuthenticated(!!token); // !! converts to boolean
    setLoading(false);
  }, []);

  /**
   * Handle login: Save token and update state
   */
  const handleLogin = (token) => {
    localStorage.setItem('token', token);
    setIsAuthenticated(true);
  };

  /**
   * Handle logout: Remove token and update state
   */
  const handleLogout = () => {
    localStorage.removeItem('token');
    localStorage.removeItem('userEmail');
    localStorage.removeItem('userName');
    setIsAuthenticated(false);
  };

  if (loading) {
    return <div className="loading">Loading...</div>;
  }

  return (
    <Router>
      <div className="App">
        <Routes>
          {/* Public Routes */}
          <Route 
            path="/login" 
            element={
              isAuthenticated ? 
                <Navigate to="/tasks" /> : 
                <Login onLogin={handleLogin} />
            } 
          />
          <Route 
            path="/register" 
            element={
              isAuthenticated ? 
                <Navigate to="/tasks" /> : 
                <Register onLogin={handleLogin} />
            } 
          />
          
          {/* Protected Routes */}
          <Route 
            path="/tasks" 
            element={
              isAuthenticated ? 
                <TaskList onLogout={handleLogout} /> : 
                <Navigate to="/login" />
            } 
          />
          
          {/* Default route */}
          <Route 
            path="/" 
            element={
              <Navigate to={isAuthenticated ? "/tasks" : "/login"} />
            } 
          />
        </Routes>
      </div>
    </Router>
  );
}

export default App;

