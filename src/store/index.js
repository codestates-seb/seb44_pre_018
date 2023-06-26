import { configureStore, createSlice } from '@reduxjs/toolkit';
import { getCookie } from 'pages/cookie';
const token = getCookie('accessToken');
const initialState = {
  name: '',
  email: '',
  isLoading: false,
  isLogin: null,
  token: '',
};
export const userSlice = createSlice({
  name: 'user',
  initialState: initialState,
  headers: {
    Authorization: `${token}`,
  },
  reducers: {
    loginUser: (state, action) => {
      return action.payload;
    },
    clearUser: (state) => {
      return initialState;
    },
  },
});
export const { loginUser, clearUser } = userSlice.actions;
export const store = configureStore({
  reducer: {
    user: userSlice.reducer,
  },
});
