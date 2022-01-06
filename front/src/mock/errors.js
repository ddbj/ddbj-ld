class ResponseError extends Error {
  constructor(status, message) {
    super(message);
    this.status = status;
  }
}

export class NotFoundError extends ResponseError {
  constructor(message = 'Not　Found') {
    super(404, message);
  }
}
export class ConflictError extends ResponseError {
  constructor(message = 'Conflict') {
    super(409, message);
  }
}
