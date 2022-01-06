import {
  FormattedMessage,
  FormattedDate,
  FormattedTime
} from 'react-intl';
import {
  ListGroup,
  ListGroupItem,
  ListGroupItemHeading
} from 'reactstrap';

export default function JvarEntrySummaryPropertiesList ({ jvarEntry }) {
  return (
    <ListGroup>
      <ListGroupItem>
        <ListGroupItemHeading>
          <FormattedMessage id="entry.uuid" />
        </ListGroupItemHeading>
        {jvarEntry.uuid}
      </ListGroupItem>
      <ListGroupItem>
        <ListGroupItemHeading>
          <FormattedMessage id="entry.label" />
        </ListGroupItemHeading>
        {jvarEntry.label}
      </ListGroupItem>
      <ListGroupItem>
        <ListGroupItemHeading>
          <FormattedMessage id="entry.revision" />
        </ListGroupItemHeading>
        {jvarEntry.revision}
      </ListGroupItem>
      <ListGroupItem>
        <ListGroupItemHeading>
          <FormattedMessage id="entry.published_revision" />
        </ListGroupItemHeading>
        {jvarEntry.publishedRevision || <FormattedMessage id="none" />}
      </ListGroupItem>
      <ListGroupItem>
        <ListGroupItemHeading>
          <FormattedMessage id="entry.published_at" />
        </ListGroupItemHeading>
        {jvarEntry.publishedAt ? (
          <time dateTime={jvarEntry.publishedAt}>
            <FormattedDate value={jvarEntry.publishedAt} />{' '}<FormattedTime value={jvarEntry.publishedAt} />
          </time>
        ) : (
          <FormattedMessage id="none" />
        )}
      </ListGroupItem>
      <ListGroupItem>
        <ListGroupItemHeading>
          <FormattedMessage id="entry.jvar.type" />
        </ListGroupItemHeading>
        {jvarEntry.type}
      </ListGroupItem>
      <ListGroupItem>
        <ListGroupItemHeading>
          <FormattedMessage id="entry.status" />
        </ListGroupItemHeading>
        {jvarEntry.status}
      </ListGroupItem>
      <ListGroupItem>
        <ListGroupItemHeading>
          <FormattedMessage id="entry.validation_status" />
        </ListGroupItemHeading>
        {jvarEntry.validationStatus}
      </ListGroupItem>
      <ListGroupItem>
        <ListGroupItemHeading>
          <FormattedMessage id="entry.created_at" />
        </ListGroupItemHeading>
        {jvarEntry.createdAt ? (
          <time dateTime={jvarEntry.createdAt}>
            <FormattedDate value={jvarEntry.createdAt} />{' '}<FormattedTime value={jvarEntry.createdAt} />
          </time>
        ) : (
          <FormattedMessage id="none" />
        )}
      </ListGroupItem>
      <ListGroupItem>
        <ListGroupItemHeading>
          <FormattedMessage id="entry.updated_at" />
        </ListGroupItemHeading>
        {jvarEntry.updatedAt ? (
          <time dateTime={jvarEntry.updatedAt}>
            <FormattedDate value={jvarEntry.updatedAt} />{' '}<FormattedTime value={jvarEntry.updatedAt} />
          </time>
        ) : (
          <FormattedMessage id="none" />
        )}
      </ListGroupItem>
    </ListGroup>
  );
}
