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
    }
    return config;
  },
  (error) => Promise.reject(error)
);

api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('token');
      window.location.href = '/login';
    }
    return Promise.reject(error);
  }
);

export const authAPI = {
  register: (userData) => api.post('/auth/register', userData).then(res => res.data),
  login: (credentials) => api.post('/auth/login', credentials).then(res => res.data),
};

export const taskAPI = {
  getAll: () => api.get('/tasks').then(res => res.data),
  getById: (id) => api.get(`/tasks/${id}`).then(res => res.data),
  create: (taskData) => api.post('/tasks', taskData).then(res => res.data),
  update: (id, taskData) => api.put(`/tasks/${id}`, taskData).then(res => res.data),
  delete: (id) => api.delete(`/tasks/${id}`),
};

export default api;
