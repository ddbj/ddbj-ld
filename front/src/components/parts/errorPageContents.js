import { FormattedMessage } from 'react-intl';
import Link from 'next/link';
import { Button } from 'reactstrap';

import Stack from './Stack';

export function BadRequestPageContent () {
  return (
    <Stack direction="column" gap={5} className="py-5">
      <Stack direction="column" gap={3} className="py-2">
        <h1><FormattedMessage id="error.400" /></h1>
        <p><FormattedMessage id="error.400.message" /></p>
      </Stack>
    </Stack>
  );
}

export function UnauthorizedPageContent () {
  return (
    <Stack direction="column" gap={5} className="py-5">
      <Stack direction="column" gap={3} className="py-2">
        <h1><FormattedMessage id="error.401" /></h1>
        <p><FormattedMessage id="error.401.message" /></p>
      </Stack>
      <Stack className="my-2" gap={4}>
        <Link href="/login" passHref>
          <Button type="a" color="primary">
            <FormattedMessage id="login" />
          </Button>
        </Link>
      </Stack>
    </Stack>
  );
}

export function NotFoundPageContent () {
  return (
    <Stack direction="column" gap={5} className="py-5">
      <Stack direction="column" gap={3} className="py-2">
        <h1><FormattedMessage id="error.404" /></h1>
        <p><FormattedMessage id="error.404.message" /></p>
      </Stack>
    </Stack>
  );
}

export function InternalServerErrorPageContent () {
  return (
    <Stack direction="column" gap={5} className="py-5">
      <Stack direction="column" gap={3} className="py-2">
        <h1><FormattedMessage id="error.500" /></h1>
        <p><FormattedMessage id="error.500.message" /></p>
      </Stack>
    </Stack>
  );
}

export function UnexpectedErrorPageContent () {
  return (
    <Stack direction="column" gap={5} className="py-5">
      <Stack direction="column" gap={3} className="py-2">
        <h1><FormattedMessage id="error.unexpected" /></h1>
        <p><FormattedMessage id="error.500.message" /></p>
      </Stack>
    </Stack>
  );
}
