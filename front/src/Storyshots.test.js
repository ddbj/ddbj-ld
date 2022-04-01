/* global jest */
import initStoryshots from '@storybook/addon-storyshots';
import { setupServer } from 'msw/node';
import { handlers } from './mocks/handlers';

setupServer(...handlers);

jest.mock('react-dom', () => {
  const original = jest.requireActual('react-dom');
  return {
    ...original,
    createPortal: node => node,
  };
});

initStoryshots();
