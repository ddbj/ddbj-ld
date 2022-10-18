import { useEffect, useCallback, useState, useMemo } from 'react';
import {
  FormattedMessage,
  FormattedDate,
  FormattedTime,
  useIntl
} from 'react-intl';
import {
  Formik,
  Form
} from 'formik';
import {
  Badge,
  Button,
  Dropdown,
  DropdownToggle,
  DropdownItem,
  DropdownMenu,
  ListGroup,
  ListGroupItem
} from 'reactstrap';
import { toast } from 'react-toastify';

import {
  useCreateEntryCommentMutation,
  useUpdateEntryCommentMutation
} from '@/services/entryApi';

import { useDropdown, useModal } from '@/hooks/ui';
import {
  useCreateEntryCommentValidationSchema,
  useUpdateEntryCommentValidationSchema
} from '@/hooks/entry';

import ErrorAlert from '@/components/parts/ErrorAlert';

import EntryCommentFormFields from '@/components/parts/entry/EntryCommentFormFields';
import DeleteEntryCommentModal from './DeleteEntryCommentModal';

function CreateEntryCommentForm ({ entryUuid, onCreated }) {
  const intl = useIntl();

  const validationSchema = useCreateEntryCommentValidationSchema();
  const initialValues = useMemo(() => validationSchema.cast({
    entryUuid,
    curator: '???',
  }), [entryUuid, validationSchema]);

  const [createEntryComment, {
    isLoading: isCreating,
    isSuccess: isCreated,
    error: createError,
    fulfilledTimeStamp
  }] = useCreateEntryCommentMutation();

  const handleSubmit = useCallback(({ entryUuid, curator, comment }) => {
    createEntryComment({
      entryUuid,
      entryComment: {
        curator,
        comment
      }
    });
  }, [createEntryComment]);

  useEffect(() => {
    if (!isCreated) return;
    toast.success(intl.formatMessage({ id: 'entry.comment.succeed_to_create' }));
    onCreated && onCreated();
  }, [intl, isCreated, onCreated]);

  return currentUser && (
    // NOTE: 作成後にフォームを初期化するために key を与える
    <Formik key={fulfilledTimeStamp} initialValues={initialValues} validationSchema={validationSchema} onSubmit={handleSubmit}>
      {props => (
        <Form>
          <EntryCommentFormFields />
          <ErrorAlert error={createError} />
          <div className="d-flex flex-row-reverse justify-between">
            <Button type="submit" color="primary" disabled={isCreating || !props.isValid}>
              <FormattedMessage id="entry.comment.create" />
            </Button>
          </div>
        </Form>
      )}
    </Formik>
  );
}

function EditEntryCommentForm ({ entryComment, entryUuid, onUpdated, onCancel }) {
  const intl = useIntl();

  const validationSchema = useUpdateEntryCommentValidationSchema(comment);
  const initialValues = useMemo(() => validationSchema.cast({
    entryUuid,
    entryCommentUuid: entryComment.uuid,
    curator         : entryComment.curator,
    comment         : entryComment.comment
  }), [validationSchema, entryUuid, entryComment.uuid, entryComment.curator, entryComment.comment]);

  const [updateEntryComment, {
    isLoading: isUpdating,
    isSuccess: isUpdated,
    error: updateError
  }] = useUpdateEntryCommentMutation();

  const handleSubmit = useCallback(({
    entryUuid,
    entryCommentUuid,
    comment,
    curator
  }) => {
    updateEntryComment({
      entryUuid,
      entryCommentUuid,
      entryComment: {
        comment,
        curator
      }
    });
  }, [updateEntryComment]);

  useEffect(() => {
    if(!isUpdated) return;
    toast.success(intl.formatMessage({ id: 'entry.comment.succeed_to_edit' }));
    onUpdated && onUpdated();
  }, [intl, isUpdated, onUpdated]);

  return (
    <Formik onSubmit={handleSubmit} validationSchema={validationSchema} initialValues={initialValues}>
      {props => (
        <Form>
          <EntryCommentFormFields />
          <ErrorAlert error={updateError} />
          <div className="d-flex flex-row-reverse justify-between">
            <Button color="secondary" outline type="button" onClick={onCancel}>
              <FormattedMessage id="cancel" />
            </Button>
            <Button color="primary" type="submit" disabled={!props.isValid || isUpdating}>
              <FormattedMessage id="entry.comment.save" />
            </Button>
          </div>
        </Form>
      )}
    </Formik>
  );
}

function Author ({ author, curator = false }) {
  return (
    <div className="flex-grow-1 d-flex flex-row gap-2 align-items-center">
      <div style={{ width: '2.5rem', height: '2.5rem', lineHeight: '2.5rem' }} className="bg-light rounded-circle d-flex justify-content-center align-items-center">
        <div>{author[0]}</div>
      </div>
      <div className="fw-bold fs-6">{author}</div>
      {curator && <Badge color="secondary"><FormattedMessage id="entry.comment.visibility.curator" /></Badge>}
    </div>
  );
}

function ListItem ({ entryUuid, entryComment, onDeleted, onUpdated }) {
  const [isMenuOpened, toggleMenu] = useDropdown(false);
  const [
    isDeleteEntryCommentModalOpen,
    toggleDeleteEntryCommentModal
  ] = useModal(false);
  const [isEditing, setIsEditing] = useState(false);

  const handleUpdated = useCallback(() => {
    onUpdated && onUpdated();
    setIsEditing(false);
  }, [onUpdated]);

  // TODO: 認証情報と該当のデータとを突き合わせて操作への制御する
  return (
    <ListGroupItem key={entryComment.uuid} className="d-flex flex-column gap-3 py-3">
      {!isEditing ? (
        <>
          <div className="d-flex gap-2 justify-between align-items-center">
            <Author author={entryComment.author} curator={entryComment.curator} />
            <Dropdown isOpen={isMenuOpened} toggle={toggleMenu}>
              <DropdownToggle size="sm" outline>
                <i className="bi bi-list" />
              </DropdownToggle>
              <DropdownMenu>
                <DropdownItem onClick={() => setIsEditing(true)}>
                  <FormattedMessage id="entry.comment.edit" />
                </DropdownItem>
                <DropdownItem onClick={toggleDeleteEntryCommentModal} className="text-danger">
                  <FormattedMessage id="entry.comment.delete" />
                </DropdownItem>
                <DeleteEntryCommentModal
                  isOpen={isDeleteEntryCommentModalOpen}
                  toggle={toggleDeleteEntryCommentModal}
                  entryUuid={entryUuid} entryCommentUuid={entryComment.uuid}
                  onDeleted={onDeleted}
                />
              </DropdownMenu>
            </Dropdown>
          </div>
          <div className="fs-5">{entryComment.comment}</div>
          <div className="d-flex gap-2">
            <span>
              <FormattedMessage id="entry.comment.created_at" />
            </span>
            <time className="fs-6" dateTime={entryComment.createdAt}>
              <FormattedDate value={entryComment.createdAt} />{' '}<FormattedTime value={entryComment.createdAt} />
            </time>
          </div>
        </>
      ) : (
        <>
          <Author author={entryComment.author} curator={entryComment.curator} />
          <EditEntryCommentForm
            entryComment={entryComment} entryUuid={entryUuid}
            onCancel={() => setIsEditing(false)} onUpdated={handleUpdated} />
        </>
      )}
    </ListGroupItem>
  );
}

// REVIEW: エラーを表示するためのEntryCommentList 内で mutation を実行しているが、取得と更新の責任の所在が矛盾しているように思われる
export default function EntryCommentList ({ entryUuid, entryCommentList, onUpdated, onDeleted, onCreated }) {
  return (
    <>
      <ListGroup>
        {entryCommentList?.map(entryComment => (
          <ListItem key={entryComment.uuid}
            entryUuid={entryUuid} entryComment={entryComment}
            onUpdated={onUpdated} onDeleted={onDeleted} />))}
      </ListGroup>
      <div className="p-3">
        <CreateEntryCommentForm entryUuid={entryUuid} onCreated={onCreated} />
      </div>
    </>
  );
}
0;