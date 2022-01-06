import {
  createSlice,
  createAsyncThunk
} from '@reduxjs/toolkit';

import * as authApi from '../services/authApi';

const initialState = {
  accessToken: null,
  currentUser: null,
  isLoading  : false,
  error      : null
};

export const login = createAsyncThunk(
  'auth/login',
  async function ({ code }, { rejectWithValue }) {
    const { data, error } = await authApi.login(code);

    if (error) {
      return rejectWithValue(error);
    }

    const {
      access_token: accessToken,
      curator,
      mail,
      uid,
      uuid
    } = data;

    return {
      accessToken,
      currentUser: {
        curator,
        mail,
        uid,
        uuid
      }
    };
  }
);

export const logout = createAsyncThunk(
  'auth/logout',
  async function (_arg, { rejectWithValue }) {
    const { error } = await authApi.logout();

    if (error) return rejectWithValue(error);

    return;
  }
);

export const refreshAccessToken = createAsyncThunk(
  'auth/refreshAccessToken',
  async function (_arg, thunkAPI) {
    const {
      auth: { accessToken }
    } = thunkAPI.getState();

    const response = await authApi.refreshAccessToken(accessToken);
    const {
      accessToken: refreshedAccessToken
    } = await response.json();

    return { accessToken: refreshedAccessToken };
  }
);

const authSlice = createSlice({
  name    : 'auth',
  initialState,
  reducers: {
    init (state) {
      state.isLoading = false;
      state.error = null;
    },
    setAccessToken (state, action) {
      state.accessToken = action.payload;
    }
  },
  extraReducers: (builder) => {
    builder.addCase(login.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(login.fulfilled, (state, action) => {
      state.isLoading = false;
      const { accessToken, currentUser } = action.payload;
      state.accessToken = accessToken;
      state.currentUser = currentUser;
      state.error = null;
    });
    builder.addCase(login.rejected, (state, action) => {
      state.isLoading = false;
      state.error = action.error;
    });
    builder.addCase(logout.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(logout.fulfilled, (state) => {
      state.isLoading = false;
      state.accessToken = null;
      state.currentUser = null;
      state.error = null;
    });
    builder.addCase(logout.rejected, (state, action) => {
      state.isLoading = false;
      state.accessToken = null;
      state.currentUser = null;
      state.error = action.error;
    });
    builder.addCase(refreshAccessToken.pending, (state) => {
      state.isLoading = true;
    });
    builder.addCase(refreshAccessToken.fulfilled, (state, action) => {
      state.isLoading = false;
      state.accessToken = action.payload.accessToken;
      state.currentUser = null;
      state.error = null;
    });
    builder.addCase(refreshAccessToken.rejected, (state, action) => {
      state.isLoading = false;
      state.error = action.error;
    });
  },
});

export const { init, setAccessToken } = authSlice.actions;

export const selectAuthState = state => state.auth;
export const selectCurrentUser = state => state.auth;

export default authSlice.reducer;
