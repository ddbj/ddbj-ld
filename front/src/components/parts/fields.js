import { useCallback, useMemo } from 'react';
import { Fzf } from 'fzf';
import classNames from 'classnames';
import { useField } from 'formik';
import Downshift from 'downshift';
import {
  Input,
  InputGroup,
  FormFeedback,
  Label as RSLabel,
  FormText,
  InputGroupText
} from 'reactstrap';

export function Container ({ ...props }) {
  return <div className="d-flex flex-column gap-0" {...props} />;
}

export function Label ({ label, required, htmlFor, ...props }) {
  return label && (
    <RSLabel htmlFor={htmlFor} {...props}>
      {label}
      {required && <em className="ml-1 text-danger">*</em>}
    </RSLabel>
  );
}

export function ReadOnlyField ({ label, className, text, textColor, ...props }) {
  const [field, meta] = useField(props);
  return (
    <Container className={className}>
      {label && <Label as="div" label={label} />}
      <div className="p-2">{field.value}</div>
      {text && <FormText color={textColor}>{text}</FormText>}
      <FormFeedback>{meta.error}</FormFeedback>
    </Container>
  );
}

export function ReadOnlyLabelAndValueField({ label, value, textColor, className, text }) {
  return (
    <Container className={className}>
      {label && <Label as="div" label={label} />}
      <div className="p-2">{value}</div>
      {text && <FormText color={textColor}>{text}</FormText>}
    </Container>
  );
}

export function InputField ({ label, required = false, text, noFeedback = false, textColor = 'mute', className, prepend, append, ...props }){
  const [field, meta] = useField(props);
  return (
    <Container className={className}>
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
    </Container>
  );
};

const ITEM_COUNT = 5;

export function SelectFieldWithAutocomplete ({ label, required, text, textColor = 'mute', className, items, itemCount = ITEM_COUNT, ...props }) {
  const [field, meta, helpers] = useField(props);

  const itemLabelsFzf = useMemo (() => new Fzf(items, { selector: (item) => item.label }), [items]);

  const handleDownshiftChange = useCallback((selectedItem) => {
    helpers.setValue(selectedItem.value);
  }, [helpers]);

  const initialInputValue = useMemo(() => {
    const initialItem = items.find(item => item.value === field.value);
    if (!initialItem) return '';
    return initialItem.label;
  }, [field.value, items]);

  return (
    <Downshift
      initialInputValue={initialInputValue}
      itemToString={item => item ? item.label : ''}
      onChange={handleDownshiftChange}>
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
      }) => {
        const displayedItems = itemLabelsFzf.find(inputValue).slice(0, itemCount).map(entry => entry.item);

        const handleBlur = () => {
          helpers.setTouched(true);
        };

        return (
          <Container className={className}>
            {label && (
              <Label {...getLabelProps({
                htmlFor : props.id || props.name,
                label   : label,
                required: required,
              })} />
            )}
            <div {...getRootProps({
              className: 'dropdown'
            }, { suppressRefError: true })}>
              <Input
                type="text"
                valid={!!(meta.touched && !meta.error)}
                invalid={!!(meta.touched && meta.error)}
                {...props}
                {...getInputProps({
                  onFocus: openMenu,
                  onBlur : handleBlur
                })} />
              <ul {...getMenuProps({
                className: classNames('dropdown-menu', { show: isOpen }),
              })}>
                {displayedItems.map((item, index) => (
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
          </Container>
        );
      }}
    </Downshift>
  );
}

export function InputFieldWithAutocomplete ({ label, required, text, textColor = 'mute', className, items, itemCount = ITEM_COUNT, ...props }) {
  const [field, meta, helpers] = useField(props);

  const itemsFzf = useMemo (() => new Fzf(items), [items]);

  const handleDownshiftChange = useCallback((selectedItem) => {
    helpers.setValue(selectedItem);
  }, [helpers]);

  return (
    <Downshift
      initialInputValue={field.value}
      onChange={handleDownshiftChange}>
      {({
        getInputProps,
        getItemProps,
        getLabelProps,
        getMenuProps,
        getRootProps,
        openMenu,
        selectItem,
        isOpen,
        inputValue,
        highlightedIndex,
        selectedItem,
      }) => {
        const displayedItems = itemsFzf.find(inputValue).slice(0, itemCount).map(entry => entry.item);

        if (displayedItems.length === 0) {
          displayedItems.push(inputValue);
        }

        const handleBlur = () => {
          selectItem(inputValue);
          helpers.setTouched(true);
        };

        return (
          <Container className={className}>
            {label && <Label htmlFor={props.id || props.name} label={label} required={required} {...getLabelProps()} />}
            <div className="dropdown" {...getRootProps({}, { suppressRefError: true })}>
              <Input
                type="text"
                valid={!!(meta.touched && !meta.error)}
                invalid={!!(meta.touched && meta.error)}
                {...props}
                {...getInputProps({
                  onFocus: openMenu,
                  onBlur : handleBlur
                })} />
              <ul {...getMenuProps({
                className: classNames('dropdown-menu', { show: inputValue && isOpen }),
              })}>
                {displayedItems.map((item, index) => (
                  <li
                    key={item}
                    {...getItemProps({
                      index,
                      item,
                      className: classNames(
                        'dropdown-item',
                        { active: highlightedIndex === index },
                        { 'fw-bold': selectedItem === item }
                      )
                    })}>
                    {item}
                  </li>
                ))
                }
              </ul>
            </div>
            {text && <FormText color={textColor}>{text}</FormText>}
            {(meta.touched && meta.error) && (
              <FormFeedback>{meta.error}</FormFeedback>
            )}
          </Container>
        );
      }}
    </Downshift>
  );
}

export function SelectField ({ className, label, text, textColor = 'mute', required, children, ...props }) {
  const [field, meta] = useField(props);
  return (
    <Container className={className}>
      {label && <Label htmlFor={props.id || props.name} label={label} required={required} />}
      <Input type="select" {...field} {...props}>
        {field.value === '' && <option value=""></option>}
        {children}
      </Input>
      {text && <FormText color={textColor}>{text}</FormText>}
      {(meta.touched && meta.error) && (
        <FormFeedback>{meta.error}</FormFeedback>
      )}
    </Container>
  );
};

export function CheckBoxField ({ className, text, textColor = 'mute', children, ...props }) {
  const [field, meta] = useField({ ...props, type: 'checkbox' });
  return (
    <Container className={classNames(className, 'form-check')}>
      <RSLabel check>
        <Input type="checkbox" {...field} {...props} />{' '}{children}
      </RSLabel>
      {text && <FormText color={textColor}>{text}</FormText>}
      {(meta.touched && meta.error) && (
        <FormFeedback>{meta.error}</FormFeedback>
      )}
    </Container>
  );
};

export function FieldGroup ({ label, required, htmlFor, children, ...props }) {
  return (
    <div className="d-flex flex-column gap-1" {...props}>
      {label && <Label {...{ label, required, htmlFor }} />}
      <div className="d-flex flex-row gap-2">
        {children.map((child, index) =>
          <div className="flex-grow-1 w-100" key={index}>{child}</div>
        )}
      </div>
    </div>
  );
}
