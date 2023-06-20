import { configureStore, createSlice } from '@reduxjs/toolkit';

const initialState = {
  name: '',
  email: '',
  isLoading: false,
  isLogin: null,
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
export const { loginUser, clearUser } = userSlice.actions;
export const store = configureStore({
  reducer: {
    user: userSlice.reducer,
  },
});
