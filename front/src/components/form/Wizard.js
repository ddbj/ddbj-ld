import { Children, createContext, useCallback, useContext, useEffect, useMemo, useState } from 'react';
import { Form, Formik } from 'formik';
import { FormattedMessage } from 'react-intl';
import {
  Nav,
  NavItem,
  NavLink,
  Progress,
  Button
} from 'reactstrap';

import ErrorAlert from '../ErrorAlert';
import FontAwesome from '../icons/FontAwesome';

import {
  Container,
  Header,
  Body,
  Footer,
  Negative,
  Positive
} from './Layout';

const Context = createContext({ step: 0, setStep: () => {} });

export function useWizardContext() {
  const context = useContext(Context);
  return context;
}

export function WizardStep ({ label, validationSchema, children }) {
  return (<>{children}</>);
}

function Completed () {
  return (
    <div className="p-4">
      <h1><FormattedMessage id="completed" /></h1>
    </div>
  );
}

function ErrorsLogger ({ errors }){
  useEffect(() => {
    if (!errors) return;
    console.info('Wizard has some errors:', errors);
  }, [errors]);
  return null;
}

export default function Wizard({ children, backLabel, nextLabel, submitLabel, completed, isSuccess, isError, error, isLoading, ...props }) {
  const childrenArray = Children.toArray(children);
  const [step, setStep] = useState(0);
  const currentChild = childrenArray[step];

  const isLastStep = useMemo(() => step === childrenArray.length - 1, [step, childrenArray.length]);

  const handleSubmit = useCallback(async (values, helpers) => {
    if (!isLastStep) {
      setStep((s) => s + 1);
      helpers.setTouched({});
      window.scrollTo(0, 0);
      return;
    }
    await props.onSubmit(values, helpers);
  }, [isLastStep, props]);

  const handleBack = useCallback(() => {
    setStep((s) => Math.max(0, s - 1));
    window.scrollTo(0, 0);
  }, []);

  useEffect(() => {
    if (isSuccess) {
      window.scrollTo(0, 0);
    }
  }, [isSuccess]);

  return (
    <Context.Provider value={{ step, setStep }}>
      <Formik {...props} onSubmit={handleSubmit} validationSchema={currentChild.props.validationSchema}>
        {({ isValid, errors }) => (
          <>
            {isSuccess ? (completed || <Completed />) : (
              <Form autoComplete="off">
                <Container>
                  <Header>
                    <div className="d-flex flex-column gap-1 gap-md-2">
                      <Nav className="d-flex flex-wrap flex-column flex-md-row" pills>
                        {childrenArray.map((child, index) =>
                          <NavItem key={index} className="flex-grow-1">
                            <NavLink
                              onClick={() => setStep(index)}
                              active={index === step}
                              disabled={index > step}
                              tag="button"
                              type="button" className="d-block w-100">
                              {child.props.label}
                            </NavLink>
                          </NavItem>
                        )}
                      </Nav>
                      <Progress  value={step + 1} min={0} max={childrenArray.length}   />
                    </div>
                  </Header>
                  <Body>
                    <h2>{currentChild.props.label}</h2>
                    {currentChild}
                    <ErrorAlert error={error} />
                  </Body>
                  <Footer>
                    <Positive>
                      <Button type="submit" color="primary" disabled={!isValid || isLoading}>
                        {isLastStep ? (
                          <>
                            <FontAwesome name="splash" /> {submitLabel || <FormattedMessage id="submit" />}
                          </>
                        ) : (
                          <>
                            {nextLabel || <FormattedMessage id="go_next" />}
                          </>
                        )}
                      </Button>
                    </Positive>
                    {step > 0 && (
                      <Negative>
                        <Button outline color="primary" onClick={handleBack}>
                          {backLabel || <FormattedMessage id="go_back" />}
                        </Button>
                      </Negative>
                    )}
                  </Footer>
                </Container>
                <ErrorsLogger errors={errors} />
              </Form>
            )}
          </>
        )}
      </Formik>
    </Context.Provider>
  );
}
