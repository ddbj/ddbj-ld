import classNames from 'classnames';

export default function PageHeader ({ className = '', ...props }) {
  return <header className={classNames(className, 'd-flex flex-row gap-3')}  {...props} />;
}
