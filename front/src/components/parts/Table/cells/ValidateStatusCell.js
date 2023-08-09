import { ValidateStatusLabel } from '../../entry/labels';

export default function ValidateStatusCell ({ value }) {
  return (
    <ValidateStatusLabel validationStatus={value} />
  );
}
