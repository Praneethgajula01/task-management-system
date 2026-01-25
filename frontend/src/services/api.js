import axios from 'axios';

const api = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

api.interceptors.request.use(
  (config) => {
    const token = localStorage.getItem('token');
    if (token) {
      config.headers.Authorization = `Bearer ${token}`;
      console.log('Token added to request:', token.substring(0, 20) + '...');
    } else {
      console.warn('No token found in localStorage');
    }
    return config;
  },
  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (response) => response,
  (error) => {
    // Log error for debugging
    console.error('API Error:', {
      status: error.response?.status,
      message: error.response?.data?.message,
      data: error.response?.data,
    });
    
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export const authAPI = {
  register: (userData) => api.post('/api/auth/register', userData).then(res => res.data),
  login: (credentials) => api.post('/api/auth/login', credentials).then(res => res.data),
};

export const taskAPI = {
  getAll: () => api.get('/api/tasks').then(res => res.data),
  getById: (id) => api.get(`/api/tasks/${id}`).then(res => res.data),
  create: (taskData) => {
    console.log('Creating task:', taskData);
    return api.post('/api/tasks', taskData).then(res => {
      console.log('Task created:', res.data);
      return res.data;
    });
  },
  update: (id, taskData) => {
    console.log('Updating task:', id, taskData);
    return api.put(`/api/tasks/${id}`, taskData).then(res => {
      console.log('Task updated:', res.data);
      return res.data;
    });
  },
  delete: (id) => {
    console.log('Deleting task:', id);
    return api.delete(`/api/tasks/${id}`).then(res => {
      console.log('Task deleted');
      return res.data;
    });
  },
};

export default api;
