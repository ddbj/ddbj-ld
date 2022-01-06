function isBrowser () {
  return typeof window !== 'undefined';
}

export function createResourceGetter(resourceKey) {
  return function getResource (){
    if (!isBrowser()) return;
    const json = localStorage.getItem(resourceKey);
    return JSON.parse(json);
  };
}

export function createResourceSetter(resourceKey) {
  return function setResource (resource){
    if (!isBrowser()) return;
    const json = JSON.stringify(resource);
    localStorage.setItem(resourceKey, json);
  };
}

export function createResourceItemGetter(resourceKey) {
  const getResource = createResourceGetter(resourceKey);

  return function getResourceItem (itemId){
    if (!isBrowser()) return;
    const resource = getResource();
    return resource[itemId];
  };
}

export function createResourceItemSetter(resourceKey) {
  const getResource = createResourceGetter(resourceKey);
  const setResource = createResourceSetter(resourceKey);

  return function ResourceItemSetter (resourceItemId, resourceItem){
    if (!isBrowser()) return;
    const resource = getResource();
    resource[resourceItemId] = resourceItem;
    setResource(resource);
  };
}

export function initializeResource (resourceKey, defaultResource = {}, forceInitialize = false) {
  if (!isBrowser()) return;

  const getResource = createResourceGetter(resourceKey);
  const setResource = createResourceSetter(resourceKey);
  const resource = getResource();
  if (forceInitialize || resource === null) {
    setResource(defaultResource);
  }
}
