import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/transfers';

export const createTransfer = async (payload, token) => {
  const response = await axios.post(BASE_URL, payload, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};

export const fetchTransfers = async (token) => {
  const response = await axios.get(BASE_URL, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};
