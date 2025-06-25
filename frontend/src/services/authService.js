import axios from 'axios';

export const loginUser = async (username, password) => {
  const response = await axios.post('http://localhost:8080/api/auth/login', {
    username,
    password,
  });
  return response.data;
};
