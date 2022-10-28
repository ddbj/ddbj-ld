export default function Page ({ ...props }) {
  return <main {...props} />;
}

export function PageTitle ({ ...props })  {
  return <h1 {...props} />;
}

export function PageSection ({ ...props }) {
  return <section {...props} />;
}

export function PageSectionTitle ({ ...props }) {
  return <h2 {...props} />;
}

export function PageHeader ({ ...props }) {
  return <div {...props} />;
}
