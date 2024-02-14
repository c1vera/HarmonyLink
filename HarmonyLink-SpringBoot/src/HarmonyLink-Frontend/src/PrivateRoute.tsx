// src/components/PrivateRoute.tsx
import React, { ReactNode } from 'react';
import { Navigate } from 'react-router-dom';
import { useSelector } from 'react-redux';
import { AppState } from './redux/store';

interface PrivateRouteProps {
  children: ReactNode;
}

const PrivateRoute = ({ children }: PrivateRouteProps) => {
  const userInfo = useSelector((state: AppState) => state.user.userInfo);

  return userInfo ? <>{children}</> : <Navigate to="/login" />;
};

export default PrivateRoute;
