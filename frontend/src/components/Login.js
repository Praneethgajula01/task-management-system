import React, { useState } from 'react';
import { Link } from 'react-router-dom';
import { authAPI } from '../services/api';

/**
 * Login Component
 * 
 * React Hooks:
 * - useState: Manages form state (email, password, errors)
 * - Component re-renders when state changes
 * 
 * Controlled Components:
 * - Input values controlled by React state
 * - onChange updates state
 * - Form submission uses current state
 */
function Login({ onLogin }) {
  const [formData, setFormData] = useState({
    email: '',
    password: '',
  });
  const [error, setError] = useState('');
  const [loading, setLoading] = useState(false);

  /**
   * Handle input changes
   * Updates state when user types
   */
  const handleChange = (e) => {
    setFormData({
      ...formData, // Spread operator: keeps existing values
      [e.target.name]: e.target.value, // Updates specific field
    });
    setError(''); // Clear error when user types
  };

  /**
   * Handle form submission
   * Prevents default form submission
   * Calls API to login
   */
  const handleSubmit = async (e) => {
    e.preventDefault(); // Prevents page refresh
    setError('');
    setLoading(true);

    try {
      // Call API
      const response = await authAPI.login(formData);
      
      // Save token and user info, notify parent component
      localStorage.setItem('userEmail', response.email);
      localStorage.setItem('userName', response.name);
      onLogin(response.token);
    } catch (err) {
      // Handle error
      setError(err.response?.data?.message || 'Invalid email or password');
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="auth-container">
      <div className="auth-card">
        <h2>Login</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="email">Email</label>
            <input
              type="email"
              id="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              required
              placeholder="Enter your email"
            />
          </div>

          <div className="form-group">
            <label htmlFor="password">Password</label>
            <input
              type="password"
              id="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              required
              placeholder="Enter your password"
            />
          </div>

          {error && <div className="error-message">{error}</div>}

          <button 
            type="submit" 
            className="btn btn-primary" 
            style={{ width: '100%' }}
            disabled={loading}
          >
            {loading ? 'Logging in...' : 'Login'}
          </button>
        </form>

        <div className="auth-link">
          Don't have an account? <Link to="/register">Register here</Link>
        </div>
      </div>
    </div>
  );
}

export default Login;

