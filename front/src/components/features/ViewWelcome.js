import { FormattedMessage } from 'react-intl';
import Link from 'next/link';
import {
  Container,
  Card, CardTitle, CardBody, CardText,
  Row, Col,
} from 'reactstrap';

export default function ViewWelcome () {
  return (
    <Container className="p-4">
      <header className="py-4 mb-4">
        <h1>
          <FormattedMessage id="welcome" />
        </h1>
      </header>
      <Row>
        <Col xs={12} sm={6} md={4}>
          <Link href="/search">
            <Card>
              <CardBody>
                <CardTitle tag="h2" className="mb-3">
                  <FormattedMessage id="search" />
                </CardTitle>
                <CardText className="py-3">
                  <FormattedMessage id="search.description" />
                </CardText>
              </CardBody>
            </Card>
          </Link>
        </Col>
      </Row>
    </Container>
  );
}
