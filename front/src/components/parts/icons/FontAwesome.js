import classNames from 'classnames';

import 'font-awesome/css/font-awesome.css';

// @import "../../node_modules/font-awesome/css/font-awesome.css";

export default function FontAwesome ({ name, className, ...props }){
  return <i className={classNames('fa', `fa-${name}`, className)} {...props} />;
}
