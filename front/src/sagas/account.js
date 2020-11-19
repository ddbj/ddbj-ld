import { all, takeEvery, put, call, fork } from 'redux-saga/effects'
import { toast } from 'react-toastify'

import * as accountApi from '../api/account'
import * as accountAction from '../actions/account'

function * getInvitationList() {
  yield takeEvery(accountAction.GET_INVITATION_LIST, function * (action) {
    try {
      const invitationList = yield call(accountApi.getInvitationList, action.payload.projectId)

      // TODO セットする処理
    } catch (error) {
      toast.error(error.message)
    }
  })
}

export default function* saga(getState) {
  yield all([
    fork(getInvitationList),
  ])
}