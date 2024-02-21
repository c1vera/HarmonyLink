import { configureStore, combineReducers } from '@reduxjs/toolkit';
import { userReducer } from './reducers/userReducer'; 
import { persistStore, persistReducer } from 'redux-persist';
import storage from 'redux-persist/lib/storage'; // localStorage를 사용할 저장소로 import

// Persist를 위한 설정
const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['user'], // 여기에 유지하고 싶은 상태의 리듀서 이름을 배열 형태로 추가
  // blacklist: [], // 유지하지 않을 상태의 리듀서 이름을 배열 형태로 추가
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
