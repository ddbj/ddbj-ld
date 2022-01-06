import { FormattedMessage } from 'react-intl';
import {
  ListGroup,
  ListGroupItem,
  ListGroupItemHeading
} from 'reactstrap';

import { EntryRequestTypeLabel } from '../../labels/entry';

export default function DeleteEntryRequestFieldSet ({ entryRequest }) {
  return (
    <ListGroup>
      <ListGroupItem>
        <ListGroupItemHeading>
          <FormattedMessage id="entry.request.author" />
        </ListGroupItemHeading>
        {entryRequest.author}
      </ListGroupItem>
      <ListGroupItem>
        <ListGroupItemHeading>
          <FormattedMessage id="entry.request.type" />
        </ListGroupItemHeading>
        <EntryRequestTypeLabel requestType={entryRequest.type} />
      </ListGroupItem>
      <ListGroupItem>
        <ListGroupItemHeading>
          <FormattedMessage id="entry.request.comment" />
        </ListGroupItemHeading>
        {entryRequest.comment}
      </ListGroupItem>
    </ListGroup>
  );
}
