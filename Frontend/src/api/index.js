import axios from 'axios';

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || '/';

// Create axios instance
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json',
  },
});

// Request interceptor to add JWT token
api.interceptors.request.use((config) => {
  const token = localStorage.getItem('jwt');
  if (token) {
    config.headers.Authorization = `Bearer ${token}`;
  }
  return config;
});

// Response interceptor for error handling
api.interceptors.response.use(
  (response) => response,
  (error) => {
    if (error.response?.status === 401) {
      localStorage.removeItem('jwt');
      localStorage.removeItem('user');
      window.location.href = '/signin';
    }
    return Promise.reject(error);
  }
);

// Auth API
export const authAPI = {
  signup: (userData) => api.post('/auth/signup', userData),
  signin: (credentials) => api.post('/auth/signin', credentials),
};

// User API
export const userAPI = {
  getProfile: () => api.get('/api/user/profile'),
  getAllUsers: () => api.get('/api/user/all'),
  updateProfile: (profileData) => api.put('/api/user/profile', profileData),
  deleteUser: (userId) => api.delete(`/api/user/delete/${userId}`),
};

// Task API
export const taskAPI = {
  createTask: (taskData) => api.post('/api/tasks/create-user', taskData),
  getTask: (id) => api.get(`/api/tasks/get-task/${id}`),
  getAssignedTasks: (userId, status, page = 0, size = 20) => {
    const params = new URLSearchParams();
    if (status) params.append('status', status);
    params.append('page', page);
    params.append('size', size);
    return api.get(`/api/tasks/assigned-users-task/${userId}?${params}`);
  },
  getAllTasks: (status, page = 0, size = 20) => {
    const params = new URLSearchParams();
    if (status) params.append('status', status);
    params.append('page', page);
    params.append('size', size);
    return api.get(`/api/tasks/get-all-users?${params}`);
  },
  getVisibleTasks: (status, page = 0, size = 20) => {
    const params = new URLSearchParams();
    if (status) params.append('status', status);
    params.append('page', page);
    params.append('size', size);
    return api.get(`/api/tasks/visible-tasks?${params}`);
  },
  updateTask: (id, taskData) => api.put(`/api/tasks/update/${id}`, taskData),
  assignTask: (userId, taskId) => api.put(`/api/tasks/${userId}/user/${taskId}/assigned`),
  completeTask: (id) => api.put(`/api/tasks/complete/${id}`),
  deleteTask: (id) => api.delete(`/api/tasks/delete/${id}`),
  getTaskHistory: (taskId) => api.get(`/api/tasks/history/${taskId}`),
  getMyTasks: (status) => axios.get(`/api/tasks/my-tasks?status=${status}`),
};

// Submission API
export const submissionAPI = {
  submitTask: (taskId, gitHubLink) => api.post(`/api/submission/submit-task?taskId=${taskId}&gitHubLink=${encodeURIComponent(gitHubLink)}`),
  getAllSubmissions: (page = 0, size = 20) => {
    return api.get(`/api/submission/get-all-submissions?page=${page}&size=${size}`);
  },
  getTaskSubmissions: (taskId, page = 0, size = 20) => {
    return api.get(`/api/submission/get-task-submissions-by-task-id/${taskId}?page=${page}&size=${size}`);
  },
  updateSubmissionStatus: (submissionId, status) => api.put(`/api/submission/accept-decline-submission/${submissionId}?status=${status}`),
  addComment: (submissionId, comment) => api.post(`/api/submission/comment/${submissionId}?comment=${encodeURIComponent(comment)}`),
  getComments: (submissionId) => api.get(`/api/submission/comments/${submissionId}`),
};

export default api;