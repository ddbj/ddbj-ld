import { createSlice } from '@reduxjs/toolkit';

const initialState = {
  isSideBarOpened       : false,
  isSideBarStatic       : false,
  openedSideBarGroupName: null
};

const navigationSlice = createSlice({
  name    : 'navigation',
  initialState,
  reducers: {
    openSideBar(state, { payload: { isSideBarStatic = false } }) {
      state.isSideBarOpened = true;
      state.isSideBarStatic = state.isSideBarStatic || isSideBarStatic;
    },
    closeSideBar(state) {
      state.isSideBarOpened = false;
      state.isSideBarStatic = false;
    },
    toggleSideBar(state) {
      if (state.isSideBarStatic) {
        state.isSideBarOpened = !state.isSideBarOpened;
        state.isSideBarStatic = state.isSideBarOpened;
      } else {
        state.isSideBarStatic = true;
      }
    },
    openSidebarGroup(state, { payload: { sideBarGroupName } }) {
      state.openedSideBarGroupName = sideBarGroupName;
    },
    closeSideBarGroup(state) {
      state.openedSideBarGroupName = null;
    },
    toggleSidebarGroup(state, { payload: { sideBarGroupName } }) {
      state.openedSideBarGroupName = state.openedSideBarGroupName === sideBarGroupName ? null : sideBarGroupName;
    },
  }
});

export const selectNavigationState = state => state.navigation;

export const {
  openSideBar,
  closeSideBar,
  toggleSideBar,
  openSidebarGroup,
  closeSideBarGroup,
  toggleSidebarGroup
} = navigationSlice.actions;

export default navigationSlice.reducer;
