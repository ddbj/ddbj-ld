import { FormattedMessage } from 'react-intl';
import {
  ListGroup,
  ListGroupItem,
  ListGroupItemHeading
} from 'reactstrap';

import {
  ValidateStatusLabel,
  EntryFileTypeLabel
} from '../../labels/entry';

export default function DeleteEntryFileFieldSet ({ entryFile }) {
  return (
    <ListGroup>
      <ListGroupItem>
        <ListGroupItemHeading>
          <FormattedMessage id="entry.file.name" />
        </ListGroupItemHeading>
        {entryFile.name}
      </ListGroupItem>
      <ListGroupItem>
        <ListGroupItemHeading>
          <FormattedMessage id="entry.file.type" />
        </ListGroupItemHeading>
        <EntryFileTypeLabel fileType={entryFile.type} />
      </ListGroupItem>
      <ListGroupItem>
        <ListGroupItemHeading>
          <FormattedMessage id="entry.file.validation_status" />
        </ListGroupItemHeading>
        <ValidateStatusLabel validationStatus={entryFile.validationStatus} />
      </ListGroupItem>
    </ListGroup>
  );
}
