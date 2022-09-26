import {
  createContext,
  useContext,
  useCallback,
  useEffect,
  useState
} from 'react';

const Context = createContext();

function distance (query) {
  return Math.abs(document.querySelector(query)?.getBoundingClientRect().top);
}

const UPDATE_EVENTS = Object.freeze(['scroll', 'resize']);

export function Provider ({ hrefs, ...props }) {
  const [{ active }, setState] = useState({ active: null });

  useEffect(() => {
    function handle() {
      const active = Object.values(hrefs).reduce((active, href) => {
        if (!active) return href;
        return distance(active) < distance(href) ? active : href;
      }, null);
      setState(s => ({ ...s, active }));
    }
    UPDATE_EVENTS.forEach((eventName) =>
      window.addEventListener(eventName, handle)
    );
    handle();
    return function cleanup() {
      UPDATE_EVENTS.forEach((eventName) =>
        window.removeEventListener(eventName, handle)
      );
    };
  }, [hrefs]);

  return <Context.Provider value={{ active }} {...props} />;
}

export function useActive () {
  const { active } = useContext(Context);
  return active;
}
