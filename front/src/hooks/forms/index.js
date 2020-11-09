import {useCallback, useState} from "react";

export const useInput = (defaultValue = '') => {
    const [value, setValue] = useState(defaultValue);

    const updateInputHandler = useCallback(event => setValue(event.target.value), []);

    return [value, updateInputHandler, setValue];
}
