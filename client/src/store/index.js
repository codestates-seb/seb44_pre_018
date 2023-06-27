import { configureStore, createSlice } from '@reduxjs/toolkit';
import { getCookie } from 'pages/cookie';
import { combineReducers } from '@reduxjs/toolkit';
import { persistReducer, persistStore } from 'redux-persist';
import storage from 'redux-persist/lib/storage';

const token = getCookie('accessToken');
const initialState = {
  name: '',
  email: '',
  isLogin: false,
  phone: '',
  token: '',
};

export const userSlice = createSlice({
  name: 'user',
  initialState: initialState,
  reducers: {
    loginUser: (state, action) => {
      return action.payload;
    },
    clearUser: (state) => {
      return initialState;
    },
  },
});

const persistConfig = {
  key: 'root',
  storage,
  whitelist: ['user'],
};

export const { loginUser, clearUser } = userSlice.actions;

const rootReducer = combineReducers({
  user: userSlice.reducer,
});

const persistedReducer = persistReducer(persistConfig, rootReducer);

export const store = configureStore({
  reducer: persistedReducer,
});

export const persistor = persistStore(store);
