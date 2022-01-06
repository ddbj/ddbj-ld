import { useCallback, useMemo } from 'react';
import { useIntl } from 'react-intl';
import { Input } from 'reactstrap';

export default function DynamicSelectFilter ({
  column: {
    id,
    preFilteredRows,
    filterValue,
    setFilter
  }
}) {
  const intl = useIntl();

  const options = useMemo(() => {
    const options = new Set();
    preFilteredRows.forEach(row => {
      options.add(row.values[id]);
    });
    return [...options.values()];
  }, [id, preFilteredRows]);

  const changeHandler = useCallback(({ target: { value } }) => setFilter(value || undefined), [setFilter]);

  return (
    <Input type="select" bsSize="sm" value={filterValue} onChange={changeHandler}>
      <option value="">{intl.formatMessage({ id: 'table.all' })}</option>
      {options.map((option, i) => (
        <option key={i} value={option}>
          {option}
        </option>
      ))}
    </Input>
  );
}
