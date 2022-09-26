import { FormattedMessage } from 'react-intl';
import { Row, Col, Card, CardTitle } from 'reactstrap';

export default function ViewWelcome () {

  return (
    <>
      <h1>
        <FormattedMessage id="welcome" />
      </h1>
      <p>
        <FormattedMessage id="welcome.message" />
      </p>
      <Row>
        <Col>
          <Card>
            <CardTitle></CardTitle>
          </Card>
        </Col>
        <Col>
        </Col>
      </Row>
    </>
  );
}
