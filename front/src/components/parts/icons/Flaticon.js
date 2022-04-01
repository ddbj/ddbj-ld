import classNames from 'classnames';

export default function Flaticon ({ name, className, ...props }){
  return <i className={classNames('fi', `flaticon-${name}`, className)} {...props} />;
}
