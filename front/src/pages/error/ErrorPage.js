import React from 'react';
import {
  Container,
  Form,
  FormGroup,
  Input,
  Button,
} from 'reactstrap';
import { Link } from 'react-router-dom';

import s from './ErrorPage.module.scss';

class ErrorPage extends React.Component {
  render() {
    return (
      <div className={s.errorPage}>
        <Container>
          <div className={`${s.errorContainer} mx-auto`}>
            <h1 className={s.errorCode}>404</h1>
            <p className={s.errorInfo}>
              Opps, it seems that this page does not exist.
            </p>
            <p className={[s.errorHelp, 'mb-3'].join(' ')}>
              If you are sure it should, search for it.
            </p>
            <Form method="get">
              <FormGroup>
                <Input className="input-no-border" type="text" placeholder="Search Pages" />
              </FormGroup>
              <Link to="app/extra/search">
                <Button className={s.errorBtn} type="submit" color="success">
                  Search <i className="fa fa-search text-white ml-xs" />
                </Button>
              </Link>
            </Form>
          </div>
          <footer className={s.pageFooter}>
            2019 &copy; Sing App - React Admin Dashboard Template.
          </footer>
        </Container>
      </div>
    );
  }
}

export default ErrorPage;
