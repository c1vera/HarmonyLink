// src/redux/store.ts

import { configureStore } from '@reduxjs/toolkit';
import { userReducer } from './reducers/userReducer';

const store = configureStore({
  reducer: {
    user: userReducer,
    // 다른 리듀서들을 여기에 추가
  },
});

export type AppState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export default store;
