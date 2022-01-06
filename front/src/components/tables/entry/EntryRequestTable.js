import { useMemo, useCallback } from 'react';
import { useIntl, FormattedMessage } from 'react-intl';
import { usePagination, useTable } from 'react-table';
import {
  Button,
  Dropdown,
  DropdownItem,
  DropdownMenu,
  DropdownToggle
} from 'reactstrap';

import {
  useApplyEntryRequestMutation,
  useRejectEntryRequestMutation
} from '../../../services/entryApi';

import { useModal, useDropdown
} from '../../../hooks/ui';

import Icon from '../../icons/Flaticon';
import Table, { EmptyTable } from '../../Table';
import { EntryRequestType } from '../../Table/cells/entry';

import DeleteEntryRequestModal from '../../modals/entry/DeleteEntryRequestModal';
import EditEntryRequestModal from '../../modals/entry/EditEntryRequestModal';
import { useEffect } from 'react';
import { toast } from 'react-toastify';

export default function EntryRequestTable ({ entryUuid, entryRequestList, onUpdated, onDeleted, onApplied, onRejected }) {
  const intl = useIntl();

  const MenuCell = useCallback(function MenuCell ({
    row: {
      original: {
        uuid: entryRequestUuid
      }
    }
  }) {
    const [isMenuOpened, toggleMenu] = useDropdown(false);
    const [
      isUpdateEntryRequestModalOpened,
      toggleUpdateEntryRequestModal
    ] = useModal(false);
    const [
      isDeleteEntryRequestModalOpened,
      toggleDeleteEntryRequestModal
    ] = useModal(false);

    const [applyEntryRequest, {
      isLoading: isApplying,
      isSuccess: isApplied,
      error: applyError
    }] = useApplyEntryRequestMutation();

    const [rejectEntryRequest, {
      isLoading: isRejecting,
      isSuccess: isRejected,
      error: rejectError
    }] = useRejectEntryRequestMutation();

    const isProcessing = useMemo(() => isApplying || isRejecting, [isApplying, isRejecting]);

    const handleApplyEntryRequest = useCallback(() => {
      applyEntryRequest({ entryUuid, entryRequestUuid });
    }, [applyEntryRequest, entryRequestUuid]);

    useEffect(() => {
      if (!isApplied) return;
      toast.success(intl.formatMessage({ id: 'entry.request.succeed_to_apply' }));
      onApplied && onApplied();
    }, [isApplied, isRejected]);

    useEffect(() => {
      if(!applyError) return;
      toast.error(intl.formatMessage({ id: 'entry.request.failed_to_apply' }));
    }, [applyError]);

    const handleRejectEntryRequest = useCallback(() => {
      rejectEntryRequest({ entryUuid, entryRequestUuid });
    }, [rejectEntryRequest, entryRequestUuid]);

    useEffect(() => {
      if (!isRejected) return;
      toast.success(intl.formatMessage({ id: 'entry.request.succeed_to_reject' }));
      onRejected && onRejected();
    }, [isRejected]);

    useEffect(() => {
      if(!rejectError) return;
      toast.error(intl.formatMessage({ id: 'entry.request.failed_to_reject' }));
    }, [rejectError]);

    return (
      <div className="d-flex justify-content-end gap-2">
        <Button
          onClick={handleApplyEntryRequest}
          size="sm" color="primary" className="text-nowrap" disabled={isProcessing}>
          <FormattedMessage id="entry.request.apply" />
        </Button>
        <Button
          onClick={handleRejectEntryRequest}
          size="sm" color="danger" className="text-nowrap" disabled={isProcessing}>
          <FormattedMessage id="entry.request.reject" />
        </Button>
        <Dropdown isOpen={isMenuOpened} toggle={toggleMenu}>
          <DropdownToggle size="sm" color="light">
            <Icon name="menu-2" />
          </DropdownToggle>
          <DropdownMenu end>
            <DropdownItem onClick={toggleUpdateEntryRequestModal}>
              <FormattedMessage id="entry.request.edit" />
            </DropdownItem>
            <EditEntryRequestModal
              isOpen={isUpdateEntryRequestModalOpened}
              toggle={toggleUpdateEntryRequestModal}
              entryUuid={entryUuid}
              entryRequestUuid={entryRequestUuid}
              onSaved={onUpdated} />
            <DropdownItem className="text-danger" onClick={toggleDeleteEntryRequestModal}>
              <FormattedMessage id="entry.request.delete" />
            </DropdownItem>
            <DeleteEntryRequestModal
              isOpen={isDeleteEntryRequestModalOpened}
              toggle={toggleDeleteEntryRequestModal}
              entryUuid={entryUuid}
              entryRequestUuid={entryRequestUuid}
              onDeleted={onDeleted} />
          </DropdownMenu>
        </Dropdown>
      </div>
    );
  }, [entryUuid, onDeleted, onUpdated]);

  const columns = useMemo(() => ([ {
    Header  : intl.formatMessage({ id: 'entry.request.author' }),
    accessor: 'author',
  }, {
    Header  : intl.formatMessage({ id: 'entry.request.type' }),
    accessor: 'type',
    Cell    : EntryRequestType,
  }, {
    Header  : intl.formatMessage({ id: 'entry.request.comment' }),
    accessor: 'comment',
  }, {
    Header  : '',
    accessor: '_menu',
    Cell    : MenuCell
  }]), [MenuCell, intl]);

  const table = useTable({
    data: entryRequestList,
    columns
  }, usePagination);

  return entryRequestList.length > 0 ? (
    <Table {...table} />
  ) : (
    <EmptyTable>
      <FormattedMessage id="entry.request.empty" />
    </EmptyTable>
  );
}
