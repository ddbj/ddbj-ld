import { useCallback, useMemo } from 'react';
import { useRouter } from 'next/router';
import { useIntl } from 'react-intl';
import { useTable, usePagination, useFilters } from 'react-table';

import Table from '@/components/parts/Table';
import DefaultFilter from '@/components/parts/Table/filters/DefaultFilter';
import DynamicSelectFilter from '@/components/parts/Table/filters/DynamicSelectFilter';
import {
  EntryStatusCell,
  EntryActiveRequestCell
} from '@/components/parts/entry/cells';

export default function JvarEntryTable ({ jvarEntryList }) {
  const router = useRouter();
  const intl = useIntl();

  const columns = useMemo(() => ([{
    Header  : intl.formatMessage({ id: 'entry.label' }),
    accessor: 'label',
    Filter  : DefaultFilter,
    filter  : 'includes',
  }, {
    Header  : intl.formatMessage({ id: 'entry.jvar.type' }),
    accessor: 'type',
    Filter  : DynamicSelectFilter,
    filter  : 'equals',
  }, {
    Header  : intl.formatMessage({ id: 'entry.status' }),
    accessor: 'status',
    Filter  : DynamicSelectFilter,
    filter  : 'equals',
    Cell    : EntryStatusCell
  },  {
    Header  : intl.formatMessage({ id: 'entry.active_request' }),
    accessor: 'activeRequest',
    Filter  : DynamicSelectFilter,
    filter  : 'equals',
    Cell    : EntryActiveRequestCell,
  }]), [intl]);

  const table = useTable({
    data: jvarEntryList,
    columns
  }, useFilters, usePagination);

  const customRowProps = useCallback(({ original: { uuid } }) => ({
    role   : 'button',
    onClick: function () {
      router.push(`/entry/jvar/${uuid}`);
    }
  }), [router]);

  return (
    <Table
      customRowProps={customRowProps}
      {...table} />
  );
}
