import classNames from 'classnames';

export default function FontAwesome ({ name, className, ...props }){
  return <i className={classNames('fa', `fa-${name}`, className)} {...props} />;
}
