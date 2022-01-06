import classNames from 'classnames';

export default function Glyphicon ({ name, className, ...props }){
  return <i className={classNames('glyphicon', `glyphicon-${name}`, className)} {...props} />;
}
