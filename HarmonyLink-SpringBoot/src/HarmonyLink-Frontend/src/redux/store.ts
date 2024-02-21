import { configureStore, combineReducers } from '@reduxjs/toolkit';
import { userReducer } from './reducers/userReducer';
import { persistStore, persistReducer } from 'redux-persist';
import sessionStorage from 'redux-persist/lib/storage/session'; // 세션 스토리지를 사용하기 위해 import

// Persist 설정을 세션 스토리지로 변경
const persistConfig = {
  key: 'root',
  storage: sessionStorage, // 여기에서 storage를 세션 스토리지로 설정
  whitelist: ['user'], // 상태 유지를 원하는 리듀서를 지정
};

// 리듀서들을 합치는 과정
const rootReducer = combineReducers({
  user: userReducer,
  // 다른 리듀서들을 여기에 추가
});

// Persist 리듀서 설정
const persistedReducer = persistReducer(persistConfig, rootReducer);

// 스토어 생성
const store = configureStore({
  reducer: persistedReducer, // Persist가 적용된 리듀서를 사용
  middleware: (getDefaultMiddleware) =>
    getDefaultMiddleware({
      serializableCheck: false,
    }),
});

// Persistor 생성
const persistor = persistStore(store);

export type AppState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;

export { store, persistor };
