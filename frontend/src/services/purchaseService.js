import axios from 'axios';

const BASE_URL = 'http://localhost:8080/api/purchases';

export const fetchPurchases = async (token) => {
  const response = await axios.get(BASE_URL, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};

export const createPurchase = async (purchase, token) => {
  const response = await axios.post(BASE_URL, purchase, {
    headers: {
      Authorization: `Bearer ${token}`,
    },
  });
  return response.data;
};
