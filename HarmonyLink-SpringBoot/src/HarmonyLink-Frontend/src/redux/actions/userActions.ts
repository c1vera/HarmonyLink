// src/redux/actions/userActions.ts

import { UserInfo, USER_LOGIN, USER_LOGOUT, UserActionTypes } from '../types/userTypes';

export const loginUser = (userInfo: UserInfo): UserActionTypes => ({
  type: USER_LOGIN,
  payload: userInfo,
});

export const logoutUser = (): UserActionTypes => ({
  type: USER_LOGOUT,
});
