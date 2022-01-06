import { baseQueryWithReauth } from './appApi';
import store from '../store';

// HACK: 適切な方法がわからない
export default async function request (args, api, extraOptions) {
  return await baseQueryWithReauth(args, {
    dispatch: store.dispatch,
    getState: store.getState,
    signal  : null,
    ...(api || {})
  }, extraOptions);
}
