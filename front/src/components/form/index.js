import classNames from 'classnames';
import {
  Label as RSLabel,
  Collapse as RSCpllapse
} from 'reactstrap';

import * as s from './Form.module.scss';

const GAP_BETWEEN_FIELDS = 'gap-3';

export function FormLabel ({ label, required, htmlFor, ...props }) {
  return label && (
    <RSLabel htmlFor={htmlFor} className="mb-1" {...props}>
      {label}
      {required && <em className="ml-1 text-danger">*</em>}
    </RSLabel>
  );
}

export function FormContainer ({ className, ...props }) {
  return <div className={classNames(s.container, className, 'w-full d-flex flex-column', GAP_BETWEEN_FIELDS) } {...props} />;
}

export function FormHeader ({ className, ...props }) {
  return <div className={classNames(className, 'w-full')} {...props} />;
}

export function FormBody (props) {
  return <div className={classNames(s.body, 'flex-column', GAP_BETWEEN_FIELDS)} {...props} />;
}

export function FormSection ({ className, ...props }) {
  return <section className={classNames(className, 'd-flex', 'flex-column', GAP_BETWEEN_FIELDS)} {...props} />;
}

export function FormSectionTitle ({ className, ...props }) {
  return <h1 className={classNames(className, 'mt-0', 'text-start', 'fs-3')} {...props} />;
}

export function FormFieldset ({ className, ...props }) {
  return <fieldset className={classNames(className, 'p-4', 'd-flex', 'flex-column', GAP_BETWEEN_FIELDS)} {...props} />;
}

export function FormFooter (props) {
  return <div className={s.footer} {...props} />;
}

export function FormPositiveActions (props) {
  return <div className={s.positive} {...props} />;
}

export function FormNegativeActions (props) {
  return <div className={s.negative} {...props} />;
}

export function FormFieldGroup ({ label, required, htmlFor, children, className, ...props }) {
  return (
    <div className={classNames('d-flex', 'flex-column', 'gap-1', className)} {...props}>
      {label && <FormLabel {...{ label, required, htmlFor }} />}
      <div className={classNames('d-flex', 'gap-3', 'flex-column', 'flex-md-row', 'align-items-start', s.fieldGroup)}>
        {children}
      </div>
    </div>
  );
}

export function FormCollapse ({ className, isOpen, ...props }) {
  return <RSCpllapse isOpen={isOpen} className={classNames({ 'd-flex': isOpen }, 'flex-column', GAP_BETWEEN_FIELDS, className)} {...props} />;
}
