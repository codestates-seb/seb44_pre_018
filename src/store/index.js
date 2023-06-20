import { configureStore, createSlice } from '@reduxjs/toolkit';

export const userSlice = createSlice({
  name: 'user',
  initialState: {
    name: '',
    email: '',
    isLogin: null,
  },
  reducers: {
    loginUser: (state, action) => {},
    clearUser: (state) => {},
  },
});
export const { loginUser, clearUser } = userSlice.actions;
export const store = configureStore({
  reducer: {
    user: userSlice.reducer,
  },
});
