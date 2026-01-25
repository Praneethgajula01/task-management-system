import React, { useState, useEffect } from 'react';
import { taskAPI } from '../services/api';

/**
 * TaskList Component
 * 
 * Main component for task management
 * Features:
 * - Display all tasks
 * - Create new tasks
 * - Edit existing tasks
 * - Delete tasks
 * - Filter by status
 */
function TaskList({ onLogout }) {
  const [tasks, setTasks] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');
  const [editingTask, setEditingTask] = useState(null);
  const [filterStatus, setFilterStatus] = useState('ALL');
  
  const [formData, setFormData] = useState({
    title: '',
    description: '',
    status: 'PENDING',
  });

  /**
   * useEffect Hook
   * Runs after component mounts (first render)
   * Fetches tasks from API
   */
  useEffect(() => {
    fetchTasks();
  }, []);

  /**
   * Fetch all tasks from API
   */
  const fetchTasks = async () => {
    try {
      setLoading(true);
      const data = await taskAPI.getAll();
      setTasks(data);
      setError('');
    } catch (err) {
      setError('Failed to load tasks. Please try again.');
      console.error('Error fetching tasks:', err);
    } finally {
      setLoading(false);
    }
  };

  /**
   * Handle form input changes
   */
  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  /**
   * Handle form submission (Create or Update)
   */
  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');

    try {
      if (editingTask) {
        // Update existing task
        await taskAPI.update(editingTask.id, formData);
      } else {
        // Create new task
        await taskAPI.create(formData);
      }
      
      // Reset form and refresh tasks
      resetForm();
      fetchTasks();
    } catch (err) {
      console.error('Submit error:', err);
      const errorMsg = err.response?.data?.message || err.message || 'Failed to save task.';
      setError(errorMsg);
    }
  };

  /**
   * Start editing a task
   */
  const handleEdit = (task) => {
    setEditingTask(task);
    setFormData({
      title: task.title,
      description: task.description || '',
      status: task.status,
    });
    // Scroll to form
    window.scrollTo({ top: 0, behavior: 'smooth' });
  };

  /**
   * Cancel editing
   */
  const handleCancel = () => {
    resetForm();
  };

  /**
   * Delete a task
   */
  const handleDelete = async (id) => {
    if (!window.confirm('Are you sure you want to delete this task?')) {
      return;
    }

    try {
      await taskAPI.delete(id);
      fetchTasks();
    } catch (err) {
      console.error('Delete error:', err);
      const errorMsg = err.response?.data?.message || err.message || 'Failed to delete task.';
      setError(errorMsg);
    }
  };

  /**
   * Reset form to initial state
   */
  const resetForm = () => {
    setFormData({
      title: '',
      description: '',
      status: 'PENDING',
    });
    setEditingTask(null);
  };

  /**
   * Filter tasks by status
   */
  const filteredTasks = filterStatus === 'ALL' 
    ? tasks 
    : tasks.filter(task => task.status === filterStatus);

  /**
   * Format date for display
   */
  const formatDate = (dateString) => {
    if (!dateString) return '';
    const date = new Date(dateString);
    return date.toLocaleDateString() + ' ' + date.toLocaleTimeString([], { hour: '2-digit', minute: '2-digit' });
  };

  /**
   * Get status badge class
   */
  const getStatusClass = (status) => {
    const statusMap = {
      PENDING: 'status-pending',
      IN_PROGRESS: 'status-in-progress',
      COMPLETED: 'status-completed',
    };
    return statusMap[status] || '';
  };

  // Get user info from localStorage (stored during login)
  const userName = localStorage.getItem('userName') || localStorage.getItem('userEmail') || 'User';

  return (
    <div className="container">
      {/* Header */}
      <div className="task-header">
        <h1>My Tasks</h1>
        <div className="user-info">
          <span>Welcome, {userName}</span>
          <button onClick={onLogout} className="btn btn-secondary">
            Logout
          </button>
        </div>
      </div>

      {/* Task Form */}
      <div className="task-form">
        <h2>{editingTask ? 'Edit Task' : 'Create New Task'}</h2>
        <form onSubmit={handleSubmit}>
          <div className="form-group">
            <label htmlFor="title">Title *</label>
            <input
              type="text"
              id="title"
              name="title"
              value={formData.title}
              onChange={handleChange}
              required
              placeholder="Enter task title"
            />
          </div>

          <div className="form-group">
            <label htmlFor="description">Description</label>
            <textarea
              id="description"
              name="description"
              value={formData.description}
              onChange={handleChange}
              placeholder="Enter task description (optional)"
            />
          </div>

          <div className="form-group">
            <label htmlFor="status">Status</label>
            <select
              id="status"
              name="status"
              value={formData.status}
              onChange={handleChange}
            >
              <option value="PENDING">Pending</option>
              <option value="IN_PROGRESS">In Progress</option>
              <option value="COMPLETED">Completed</option>
            </select>
          </div>

          {error && <div className="error-message">{error}</div>}

          <div className="form-actions">
            <button type="submit" className="btn btn-primary">
              {editingTask ? 'Update Task' : 'Create Task'}
            </button>
            {editingTask && (
              <button 
                type="button" 
                onClick={handleCancel} 
                className="btn btn-secondary"
              >
                Cancel
              </button>
            )}
          </div>
        </form>
      </div>

      {/* Filter */}
      <div className="card" style={{ marginBottom: '20px' }}>
        <div style={{ display: 'flex', alignItems: 'center', gap: '15px' }}>
          <label style={{ fontWeight: '600' }}>Filter by Status:</label>
          <select
            value={filterStatus}
            onChange={(e) => setFilterStatus(e.target.value)}
            style={{ padding: '8px', borderRadius: '5px', border: '1px solid #ddd' }}
          >
            <option value="ALL">All Tasks</option>
            <option value="PENDING">Pending</option>
            <option value="IN_PROGRESS">In Progress</option>
            <option value="COMPLETED">Completed</option>
          </select>
          <span style={{ color: '#666', marginLeft: 'auto' }}>
            Showing {filteredTasks.length} of {tasks.length} tasks
          </span>
        </div>
      </div>

      {/* Tasks List */}
      {loading ? (
        <div className="card">
          <p>Loading tasks...</p>
        </div>
      ) : filteredTasks.length === 0 ? (
        <div className="empty-state">
          <h3>No tasks found</h3>
          <p>
            {filterStatus === 'ALL' 
              ? 'Create your first task to get started!' 
              : `No tasks with status "${filterStatus}"`}
          </p>
        </div>
      ) : (
        <div className="tasks-container">
          {filteredTasks.map((task) => (
            <div key={task.id} className="task-card">
              <div className="task-header-section">
                <div style={{ flex: 1 }}>
                  <div className="task-title">{task.title}</div>
                  {task.description && (
                    <div className="task-description">{task.description}</div>
                  )}
                </div>
                <span className={`task-status ${getStatusClass(task.status)}`}>
                  {task.status.replace('_', ' ')}
                </span>
              </div>

              <div className="task-meta">
                <div className="task-date">
                  Created: {formatDate(task.createdAt)}
                  {task.updatedAt !== task.createdAt && (
                    <span> • Updated: {formatDate(task.updatedAt)}</span>
                  )}
                </div>
                <div className="task-actions">
                  <button
                    onClick={() => handleEdit(task)}
                    className="btn btn-primary btn-small"
                  >
                    Edit
                  </button>
                  <button
                    onClick={() => handleDelete(task.id)}
                    className="btn btn-danger btn-small"
                  >
                    Delete
                  </button>
                </div>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
}

export default TaskList;

