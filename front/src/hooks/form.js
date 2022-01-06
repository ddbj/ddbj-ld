import { useCallback, useEffect } from 'react';
import { useSelector, useDispatch } from 'react-redux';

export function useAsyncFormState (formName, defaultState) {
  const dispatch = useDispatch();

  const formState = useSelector(selectAsyncFormState(formName));

  const setState = useCallback((state) => {
    dispatch(setState(formName, state));
  }, [dispatch, formName]);

  useEffect(() => {
    dispatch(setState(formName, defaultState));
  }, [formName, defaultState, dispatch, setState]);

  return { formState, setFormState };
}
