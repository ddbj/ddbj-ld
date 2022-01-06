import { useState, useCallback, useMemo } from 'react';
import classNames from 'classnames';
import { useField } from 'formik';
import Downshift from 'downshift';
import ReCAPTCHA from 'react-google-recaptcha';
import {
  Input,
  InputGroup,
  FormFeedback,
  Label as RSLabel,
  FormText,
  InputGroupText,
  Button
} from 'reactstrap';

import { reCAPTCHASiteKey } from '../../config';

import { FormLabel as Label } from '.';

import * as s from'./Form.module.scss';
import { useIntl } from 'react-intl';

export function FieldContainer ({ className, ...props }) {
  return (
    <div className={classNames(s.field, 'd-flex', 'flex-column', 'gap-1', className)} {...props} />
  );
}

export function ReadOnlyField ({ label, className, text, textColor, ...props }) {
  const [field, meta] = useField(props);
  return (
    <FieldContainer className={className}>
      {label && <Label as="div" label={label} />}
      <div className="p-2">{field.value}</div>
      {text && <FormText color={textColor}>{text}</FormText>}
      <FormFeedback>{meta.error}</FormFeedback>
    </FieldContainer>
  );
}

export function ReadOnlyLabelAndValueField({ label, value, className, text }) {
  return (
    <FieldContainer className={className}>
      {label && <Label as="div" label={label} />}
      <div className="p-2">{value}</div>
      {text && <FormText color={textColor}>{text}</FormText>}
    </FieldContainer>
  );
}

export function InputField ({ label, required, text, noFeedback = false, textColor = 'mute', className, prepend, append, ...props }){
  const [field, meta] = useField(props);

  return (
    <FieldContainer className={className}>
      {label && <Label htmlFor={props.id || props.name} label={label} required={required} />}
      <InputGroup className={classNames({
        'is-valid'  : Boolean(meta.touched && !meta.error),
        'is-invalid': Boolean(meta.touched && meta.error),
      })}>
        {typeof prepend !== 'undefined' && (
          <InputGroupText>{prepend}</InputGroupText>
        )}
        <Input
          valid={!noFeedback && !!(meta.touched && !meta.error)}
          invalid={!noFeedback && !!(meta.touched && meta.error)}
          addon={typeof prepend !== 'undefined' || typeof append !== 'undefined'}
          {...field} {...props} />
        {typeof append !== 'undefined' && (
          <InputGroupText>{append}</InputGroupText>
        )}
      </InputGroup>
      {text && <FormText color={textColor}>{text}</FormText>}
      <FormFeedback>{meta.error}</FormFeedback>
    </FieldContainer>
  );
};

export function InputFieldWithAutocomplete ({ label, required, text, textColor = 'mute', className, items, ...props }) {
  const [field, meta, helpers] = useField(props);

  const handleDownshiftChange = useCallback((selection) => {
    helpers.setValue(selection.value);
  }, [helpers]);

  const itemToString = useCallback(item => (item ? item.label : ''), []);
  const handleBlur = useCallback(event => {
    field.onBlur(event);
  }, [field]);

  const initialSelectedItem = useMemo(() => {
    return items.find(item => item.value === field.value) || ({
      value: '',
      label: ''
    });
  }, [items, field.value]);

  return (
    <Downshift
      initialInputValue={initialSelectedItem.label}
      initialSelectedItem={initialSelectedItem}
      onChange={handleDownshiftChange}
      itemToString={itemToString}>
      {({
        getInputProps,
        getItemProps,
        getLabelProps,
        getMenuProps,
        getRootProps,
        openMenu,
        isOpen,
        inputValue,
        highlightedIndex,
        selectedItem,
      }) => (
        <FieldContainer className={className}>
          {label && <Label htmlFor={props.id || props.name} label={label} required={required} {...getLabelProps()} />}
          <div className="dropdown" {...getRootProps({}, { suppressRefError: true })}>
            <Input
              type="text" {...props}
              valid={!!(meta.touched && !meta.error)}
              invalid={!!(meta.touched && meta.error)}
              {...getInputProps({
                onFocus: openMenu,
                onBlur : handleBlur
              })} />
            <ul className={classNames('dropdown-menu', { show: isOpen })} {...getMenuProps()}>
              {items
                .filter(item => !inputValue || inputValue === item.label)
                .map((item, index) => (
                  <li
                    key={item.value}
                    {...getItemProps({
                      index,
                      item,
                      className: classNames(
                        'dropdown-item',
                        { active: highlightedIndex === index },
                        { 'fw-bold': selectedItem === item }
                      )
                    })}>
                    {item.label}
                  </li>
                ))
              }
            </ul>
          </div>
          {text && <FormText color={textColor}>{text}</FormText>}
          {(meta.touched && meta.error) && (
            <FormFeedback>{meta.error}</FormFeedback>
          )}
        </FieldContainer>
      )}
    </Downshift>
  );
}

export function SelectField ({ className, label, text, textColor = 'mute', required, ...props }) {
  const [field, meta] = useField(props);
  return (
    <FieldContainer className={className}>
      {label && <Label htmlFor={props.id || props.name} label={label} required={required} />}
      <Input type="select" {...field} {...props} />
      {text && <FormText color={textColor}>{text}</FormText>}
      {(meta.touched && meta.error) && (
        <FormFeedback>{meta.error}</FormFeedback>
      )}
    </FieldContainer>
  );
};

export function CheckBoxField ({ className, text, type = 'checkbox', textColor = 'mute', children, ...props }) {
  const [field, meta] = useField({ ...props, type });
  return (
    <FieldContainer className={classNames(className, 'form-check')}>
      <RSLabel check>
        <Input type={type} {...field} {...props} />{' '}{children}
      </RSLabel>
      {text && <FormText color={textColor}>{text}</FormText>}
      {(meta.touched && meta.error) && (
        <FormFeedback>{meta.error}</FormFeedback>
      )}
    </FieldContainer>
  );
};

export function ReCAPTCHAField ({ label, required, text, textColor = 'mute', className, sitekey = reCAPTCHASiteKey, ...props }){
  const intl = useIntl();
  const [, meta, helper] = useField(props);

  const [showSkip, setShowSkip] = useState(false);

  const handleChange = useCallback(token => {
    helper.setValue(token);
  }, [helper]);

  const handleError = useCallback(error => {
    helper.setError(intl.formatMessage({ id: 'error.unexpected' }));

    // TODO: delete
    console.error('ReCAPTCHAField');
    setShowSkip(true);
  }, [helper, intl, setShowSkip]);

  const handleExpired = useCallback(error => {
    helper.setError(intl.formatMessage({ id: 'error.unexpected' }));

    // TODO: delete
    console.error('ReCAPTCHAField');
    setShowSkip(true);
  }, [helper, intl, setShowSkip]);

  return (
    <FieldContainer className={className}>
      {label && <Label htmlFor={props.id || props.name} label={label} required={required} />}
      <div className="align-self-center">
        <ReCAPTCHA
          onChange={handleChange}
          onErrored={handleError}
          onExpired={handleExpired}
          sitekey={sitekey} />
      </div>
      {text && <FormText color={textColor}>{text}</FormText>}
      {(meta.touched && meta.error) && (
        <FormFeedback>{meta.error}</FormFeedback>
      )}
      {showSkip && (
        <Button color="primary" outline onClick={() => handleChange('skiped')} type="button">開発用に入力を省略する</Button>
      )}
    </FieldContainer>
  );
};
