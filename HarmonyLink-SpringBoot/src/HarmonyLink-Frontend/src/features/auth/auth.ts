import axios from 'axios';
import { useDispatch } from 'react-redux';
import { loginUser, logoutUser } from './authSlice';

export const useCheckAuthStatus = () => {
  const dispatch = useDispatch();

  const checkAuthStatus = async () => {
    try {
      const response = await axios.get('/auth/check', {
        withCredentials: true,
      });
      const data = response.data;
      if (data.isAuthenticated) {
        dispatch(loginUser(data.user));
      } else {
        dispatch(logoutUser());
      }
    } catch (error) {
      console.error('인증 상태 확인 중 오류 발생:', error);
    }
  };

  return checkAuthStatus;
};
