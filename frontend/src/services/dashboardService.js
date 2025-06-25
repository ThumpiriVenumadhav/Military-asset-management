import axios from 'axios';

export const getDashboardData = async (token) => {
  const response = await axios.get('http://localhost:8080/api/dashboard', {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};
