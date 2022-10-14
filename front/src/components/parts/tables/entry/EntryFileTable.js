import { useMemo, useCallback, useEffect } from 'react';
import { useIntl, FormattedMessage } from 'react-intl';
import { useTable, useFilters, usePagination } from 'react-table';
import {
  Dropdown,
  DropdownToggle,
  DropdownMenu,
  DropdownItem
} from 'reactstrap';
import { toast } from 'react-toastify';

import { useDownlaodEntryFileMutation } from '@/services/entryApi';

import { useModal, useDropdown } from '@/hooks/ui';

import DeleteEntryFileModal from '@/components/parts/modals/entry/DeleteEntryFileModal';

import Table from '@/components/parts/Table/Table';
import DefaultFilter from '@/components/parts/Table/filters/DefaultFilter';
import DynamicSelectFilter from '@/components/parts/Table/filters/DynamicSelectFilter';
import ValidationStatusCell from '@/components/parts/Table/cells/ValidateStatusCell';

export default function EntryFileTable ({ entryUuid, entryFileList }) {
  const intl = useIntl();

  const MenuCell = useCallback(function MenuCell ({
    row: {
      original: {
        uuid: entryFileUuid,
        name: fileName,
        type: fileType
      }
    }
  }) {
    const [
      isMenuOpened,
      toggleMenu
    ] = useDropdown(false);

    const [
      isDeleteEntryFileModalOpened,
      toggleDeleteEntryFileModal
    ] = useModal(false);

    const [downlaodEntryFile, {
      isSuccess: isDownloaded,
      isError: isFailedDownload
    }] = useDownlaodEntryFileMutation();

    const handleDownload = useCallback(() => {
      downlaodEntryFile({ entryUuid, fileType, fileName });
    }, [downlaodEntryFile, fileType, fileName]);

    useEffect(() => {
      if (isFailedDownload) {
        toast.error(intl.formatMessage({ id: 'entry.file.failed_to_download' }));
      } else if(isDownloaded) {
        toast.success(intl.formatMessage({ id: 'entry.file.succeed_to_download' }));
      }
    }, [isFailedDownload, isDownloaded]);

    return (
      <div className="d-flex justify-content-end">
        <Dropdown isOpen={isMenuOpened} toggle={toggleMenu}>
          <DropdownToggle size="sm" color="light">
            <i className="bi bi-list" />
          </DropdownToggle>
          <DropdownMenu end>
            <DropdownItem onClick={handleDownload}>
              <FormattedMessage id="entry.file.download" />
            </DropdownItem>
            <DropdownItem className="text-danger" onClick={toggleDeleteEntryFileModal}>
              <FormattedMessage id="entry.file.delete" />
            </DropdownItem>
          </DropdownMenu>
        </Dropdown>
        <DeleteEntryFileModal
          isOpen={isDeleteEntryFileModalOpened}
          toggle={toggleDeleteEntryFileModal}
          entryUuid={entryUuid}
          entryFileUuid={entryFileUuid} />
      </div>
    );
  }, [entryUuid, intl]);

  const columns = useMemo(() => ([{
    Header  : intl.formatMessage({ id: 'entry.file.name' }),
    accessor: 'name',
    Filter  : DefaultFilter,
    filter  : 'includes',
  }, {
    Header  : intl.formatMessage({ id: 'entry.jvar.type' }),
    accessor: 'type',
    Filter  : DynamicSelectFilter,
    filter  : 'equals',
  }, {
    Header  : intl.formatMessage({ id: 'entry.file.validation_status' }),
    accessor: 'validationStatus',
    Filter  : DynamicSelectFilter,
    filter  : 'equals',
    Cell    : ValidationStatusCell
  }, {
    Header  : '',
    accessor: 'uuid',
    Filter  : () => null,
    Cell    : MenuCell,
  }]), [MenuCell, intl]);

  const table = useTable({
    data: entryFileList,
    columns
  }, useFilters, usePagination);

  return (
    <>
      <Table style={{ minHeight: '30rem' }} {...table}  />
    </>
  );
}
