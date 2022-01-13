import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  sidebarOpened: false,
  sidebarStatic: false,
  openedGroup  : null
};

const navigationSlice = createSlice({
  name    : 'navigation',
  initialState,
  reducers: {
    setSidebarOpened(state, action) {
      state.sidebarOpened = action.payload;
    },
    setSidebarStatic(state, action) {
      state.sidebarStatic = action.payload;
    },
    setOpenedGroup(state, action) {
      state.openedGroup = action.payload;
    },
  }
});

export const selectNavigationState = state => state.navigation;

export const {
  setSidebarOpened,
  setSidebarStatic,
  setOpenedGroup
} = navigationSlice.actions;

export default navigationSlice.reducer;
