import { useCallback } from 'react';
import { useIntl } from 'react-intl';
import { Input  } from 'reactstrap';

import * as s from './Filter.module.scss';

export default function SelectFilter ({
  options,
  column: {
    filterValue,
    setFilter,
  }
}) {
  const intl = useIntl();

  const changeHandler = useCallback((event) =>
    setFilter(event.target.value || undefined)
  , [setFilter]);

  return (
    <Input className={s.input} bsSize="sm" type="select" value={filterValue} onChange={changeHandler}>
      <option value="">{intl.formatMessage({ id: 'table.all' })}</option>
      {options.map((option) => (
        <option key={option.value} value={option.value}>
          {option.label}
        </option>
      ))}
    </Input>
  );
}
