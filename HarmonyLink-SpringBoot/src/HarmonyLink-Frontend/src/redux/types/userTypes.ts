// src/redux/types/userTypes.ts

export interface UserInfo {
  userKey: number;
  id: string;
  name: string;
  mbti: string;
  nickname: string;
}

export interface UserState {
  userInfo: UserInfo | null;
}

export const USER_LOGIN = 'USER_LOGIN';
export const USER_LOGOUT = 'USER_LOGOUT';

interface LoginAction {
  type: typeof USER_LOGIN;
  payload: UserInfo;
}

interface LogoutAction {
  type: typeof USER_LOGOUT;
}

export type UserActionTypes = LoginAction | LogoutAction;
