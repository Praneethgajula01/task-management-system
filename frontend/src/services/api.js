import axios from 'axios';

/**
 * API Service
 * 
 * Axios: HTTP client library for making API requests
 * - Easier than fetch() API
 * - Automatic JSON parsing
 * - Request/response interceptors
 * 
 * axios.create(): Creates a configured axios instance
 * baseURL: Base URL for all requests
 * headers: Default headers sent with every request
 */
const api = axios.create({
  baseURL: 'http://localhost:8080/api',
  headers: {
    'Content-Type': 'application/json',
  },
});

/**
 * Request Interceptor
 * Runs before every request
 * Adds JWT token to Authorization header if available
 */
api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

/**
 * Response Interceptor
 * Runs after every response
 * Handles errors globally
 */
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      // Unauthorized - token expired or invalid
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

/**
 * Auth API
 */
export const authAPI = {
  /**
   * Register new user
   * POST /api/auth/register
   */
  register: async (userData) => {
    const response = await api.post('/auth/register', userData);
    return response.data;
  },

  /**
   * Login user
   * POST /api/auth/login
   */
  login: async (credentials) => {
    const response = await api.post('/auth/login', credentials);
    return response.data;
  },
};

/**
 * Task API
 */
export const taskAPI = {
  /**
   * Get all tasks
   * GET /api/tasks
   */
  getAll: async () => {
    const response = await api.get('/tasks');
    return response.data;
  },

  /**
   * Get task by ID
   * GET /api/tasks/{id}
   */
  getById: async (id) => {
    const response = await api.get(`/tasks/${id}`);
    return response.data;
  },

  /**
   * Create new task
   * POST /api/tasks
   */
  create: async (taskData) => {
    const response = await api.post('/tasks', taskData);
    return response.data;
  },

  /**
   * Update task
   * PUT /api/tasks/{id}
   */
  update: async (id, taskData) => {
    const response = await api.put(`/tasks/${id}`, taskData);
    return response.data;
  },

  /**
   * Delete task
   * DELETE /api/tasks/{id}
   */
  delete: async (id) => {
    await api.delete(`/tasks/${id}`);
  },
};

export default api;

