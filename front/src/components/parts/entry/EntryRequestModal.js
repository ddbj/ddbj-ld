import * as yup from 'yup';
import { FormattedMessage } from 'react-intl';
import { Formik, Form } from 'formik';
import {
  Button,
  Modal,
  ModalHeader,
  ModalBody,
  ModalFooter
} from 'reactstrap';

export default function CreateEntryRequestModal ({
  isOpen, toggle
}) {

  const validationSchema = useMemo(() => {
    return yup.object().shape({

    });
  }, []);

  const initialValues = useMemo(() => validationSchema.cast({

  }), [validationSchema]);

  const handleSubmit = () => {
    console.log('submitted');
  };

  return (
    <Modal toggle={toggle} isOpen={isOpen}>
      <Formik validationSchema={validationSchema} initialValues={initialValues} onSubmit={handleSubmit}>
        {({ isSubmitting }) => (
          <Form>
            <ModalHeader>
              <FormattedMessage id="entry.request.creating" />
            </ModalHeader>
            <ModalBody>
            </ModalBody>
            <ModalFooter className="d-flex flex-row-reverse justify-between">
              <Button type="submit" color="primary" disabled={isSubmitting}>
                <FormattedMessage id="entry.request.create" />
              </Button>
              <Button color="secondary" outline type="button" onClick={toggle}>
                <FormattedMessage id="cancel" />
              </Button>
            </ModalFooter>
          </Form>
        )}
      </Formik>
    </Modal>
  );
}
