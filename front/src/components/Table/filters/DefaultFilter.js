import React from 'react';
import { useCallback } from 'react';
import { useIntl } from 'react-intl';
import { Input } from 'reactstrap';

import * as s from './Filter.module.scss';

export default function DefaultFilter ({
  column: {
    filterValue,
    setFilter
  }
}) {
  const intl = useIntl();

  const changeHandler = useCallback(({ target: { value } }) => {
    setFilter(value || undefined);
  }, [setFilter]);

  return (
    <Input
      bsSize="sm" className={s.input}
      value={filterValue || ''}
      placeholder={intl.formatMessage({ id: 'table.search' })}
      onChange={changeHandler} />
  );
}
