import Axios from "axios";
import { API_SERVER } from "./constant";

const axios = Axios.create({
  baseURL: `${API_SERVER}`,
  headers: { "Content-Type": "application/json" }
})

axios.interceptors.request.use(
  (config) => {
    if (config.url !== 'auth/login') {
      config.headers['Authorization'] = `Bearer ${localStorage.getItem('token')}`
      config.headers['Content-Type'] = 'application/json'
    }
    return Promise.resolve(config)
  },
  (error) => Promise.reject(error)
);

axios.interceptors.response.use(
  (response) => {
    if (response.config.url === 'auth/login') {
      localStorage.setItem('token', response.data.token);
    }
    return Promise.resolve(response)
  },
  (error) => {
    return Promise.reject(error);
  }
);

export default axios;