import classNames from 'classnames';
import * as s from './Loading.module.scss';

import FontAwesome from '../icons/FontAwesome';

export default function Loading ({ sm = false }) {
  return (
    <div className={classNames(s.loading, { [s.loading__sm]: sm })}>
      <div className={s.loading__container}>
        <FontAwesome name="spinner" className={s.loading__spinner} />
      </div>
    </div>
  );
}
