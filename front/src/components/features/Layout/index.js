import { FormattedMessage } from 'react-intl';
import Link from 'next/link';
import { useRouter } from 'next/router';
import { Container } from 'reactstrap';

export default function Layout ({ children }){
  const router = useRouter();
  return (
    <>
      <>{children}</>
      <Container>
        <div className="d-flex gap-1 py-4">
          <Link href={router.asPath} locale="ja">
            <a>
              <FormattedMessage id="language.japanese" />
            </a>
          </Link>
          <Link href={router.asPath} locale="en">
            <a>
              <FormattedMessage id="language.english" />
            </a>
          </Link>
        </div>
      </Container>
    </>
  );
};
