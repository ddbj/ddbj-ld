import { useMemo } from 'react';
import { useIntl } from 'react-intl';
import * as Yup from 'yup';

import {
  ENTRY_FILE_TYPE,
  ENTRY_REQUEST_TYPE
} from '../../constants';

export function useDeleteEntryValidationSchema () {
  const intl = useIntl();

  return useMemo(() => Yup.object().shape({
    entryUuid: Yup.string()
      .required(intl.formatMessage({ id: 'form.error.required' })),
  }), [intl]);
}

export function useSubmitEntryValidationSchema () {
  const intl = useIntl();

  return useMemo(() => Yup.object().shape({
    entryUuid: Yup.string()
      .required(intl.formatMessage({ id: 'form.error.required' }))
      .default(''),
  }), [intl]);
}

export function useValidateEntryValidationSchema () {
  const intl = useIntl();

  return useMemo(() => Yup.object().shape({
    entryUuid: Yup.string()
      .required(intl.formatMessage({ id: 'form.error.required' }))
      .default(''),
  }), [intl]);
}

export function useEntryCommentValidationSchema () {
  const intl = useIntl();

  return useMemo(() => Yup.object().shape({
    entryUuid: Yup.string()
      .required(intl.formatMessage({ id: 'form.error.required' })),
    curator: Yup.boolean()
      .required(intl.formatMessage({ id: 'form.error.required' }))
      .default(false),
    comment: Yup.string()
      .required(intl.formatMessage({ id: 'form.error.required' }))
      .default('')
  }), [intl]);
}

export function useCreateEntryCommentValidationSchema () {
  return useEntryCommentValidationSchema();
}

export function useUpdateEntryCommentValidationSchema () {
  const intl = useIntl();
  const entryCommentValidationSchema = useEntryCommentValidationSchema();

  return useMemo(() => Yup.object().shape({
    entryCommentUuid: Yup.string()
      .required(intl.formatMessage({ id: 'form.error.required' })),
    ...entryCommentValidationSchema.fields,
  }), [entryCommentValidationSchema.fields, intl]);
}

export function useDeleteEntryCommentValidationSchema() {
  const intl = useIntl();

  return useMemo(() => Yup.object().shape({
    entryUuid: Yup.string()
      .required(intl.formatMessage({ id: 'form.error.required' })),
    entryCommentUuid: Yup.string()
      .required(intl.formatMessage({ id: 'form.error.required' })),
  }), [intl]);
}

export function useUploadEntryFileValidationSchema () {
  const intl = useIntl();

  return useMemo(() => Yup.object().shape({
    entryUuid: Yup.string()
      .required(intl.formatMessage({ id: 'form.error.required' })),
    file: Yup.mixed()
      .required(intl.formatMessage({ id: 'form.error.required' }))
      .default(null),
    fileName: Yup.string()
      .required(intl.formatMessage({ id: 'form.error.required' }))
      .default(''),
    fileType: Yup.string()
      .oneOf(Object.values(ENTRY_FILE_TYPE), intl.formatMessage({ id: 'form.error.invalid_file_type' }))
      .required(intl.formatMessage({ id: 'form.error.required' }))
      .default(''),
  }), [intl]);
}

export function useDeleteEntryFileValidationSchema () {
  const intl = useIntl();

  return useMemo(() => Yup.object().shape({
    entryUuid: Yup.string()
      .required(intl.formatMessage({ id: 'form.error.required' })),
    fileName: Yup.mixed()
      .required(intl.formatMessage({ id: 'form.error.required' }))
      .default(''),
    fileType: Yup.mixed()
      .required(intl.formatMessage({ id: 'form.error.required' }))
      .default('')
  }), [intl]);
}

export function useCreateEntryRequestValidationSchema () {
  const intl = useIntl();

  return useMemo(() => Yup.object().shape({
    entryUuid: Yup.string()
      .required(intl.formatMessage({ id: 'form.error.required' })),
    type: Yup.string()
      .oneOf(Object.values(ENTRY_REQUEST_TYPE), intl.formatMessage({ id: 'form.error.invalid_value' }))
      .required(intl.formatMessage({ id: 'form.error.required' }))
      .default(''),
    comment: Yup.string()
      .default(''),
  }), [intl]);
}

export function useUpdateEntryRequestValidationSchema () {
  const intl = useIntl();

  return useMemo(() => Yup.object().shape({
    entryUuid: Yup.string()
      .required(intl.formatMessage({ id: 'form.error.required' })),
    entryRequestUuid: Yup.string()
      .required(intl.formatMessage({ id: 'form.error.required' })),
    type: Yup.string()
      .oneOf(Object.values(ENTRY_REQUEST_TYPE), intl.formatMessage({ id: 'form.error.invalid_value' }))
      .required(intl.formatMessage({ id: 'form.error.required' })),
    comment: Yup.string(),
  }), [intl]);
}

export function useEntryRequestValidationSchema() {
  const intl = useIntl();

  return useMemo(() => Yup.object().shape({
    entryUuid: Yup.string()
      .required(intl.formatMessage({ id: 'form.error.required' })),
    entryRequestUuid: Yup.mixed()
      .required(intl.formatMessage({ id: 'form.error.required' }))
  }), [intl]);
}

export function useDeleteEntryRequestValidationSchema() {
  return useEntryRequestValidationSchema();
}

export function useRejectEntryRequestValidationSchema () {
  return useEntryRequestValidationSchema();
}

export function useApplyEntryRequestValidationSchema () {
  return useEntryRequestValidationSchema();
}

export function useEntryRequestMenu (entry) {
  return useMemo(() => ({
    isCancellable: entry?.menu.requestMenu.requestToCancel,
    isUpdatable  : entry?.menu.requestMenu.requestToUpdate,

    // TODO: 動詞である部分が統一されていないので修正案を出して通ったら直す public -> publish
    isPublishable: entry?.menu.requestMenu.requestToPublic,
  }), [entry]);
}
