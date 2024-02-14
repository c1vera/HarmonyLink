// src/redux/reducers/userReducer.ts

import { UserState, UserActionTypes, USER_LOGIN, USER_LOGOUT } from '../types/userTypes';

const initialState: UserState = {
  userInfo: null,
};

export function userReducer(state = initialState, action: UserActionTypes): UserState {
  switch (action.type) {
    case USER_LOGIN:
      return { ...state, userInfo: action.payload };
    case USER_LOGOUT:
      return { ...state, userInfo: null };
    default:
      return state;
  }
}
