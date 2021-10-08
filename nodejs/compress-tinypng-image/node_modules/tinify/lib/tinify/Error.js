"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
class Error extends global.Error {
    /** @internal */
    constructor(message, type, status) {
        super();
        global.Error.captureStackTrace(this, Error);
        if (status) {
            this.status = status;
            this.message = message + ` (HTTP ${status}/${type})`;
        }
        else {
            this.message = message;
        }
    }
    /** @internal */
    static create(message, type, status) {
        let klass;
        if (!status) {
            klass = Error;
        }
        else if (status === 401 || status === 429) {
            klass = AccountError;
        }
        else if (status >= 400 && status <= 499) {
            klass = ClientError;
        }
        else if (status >= 500 && status <= 599) {
            klass = ServerError;
        }
        else {
            klass = Error;
        }
        if (!message) {
            message = "No message was provided";
        }
        return new klass(message, type, status);
    }
}
exports.Error = Error;
class AccountError extends Error {
}
exports.AccountError = AccountError;
class ClientError extends Error {
}
exports.ClientError = ClientError;
class ServerError extends Error {
}
exports.ServerError = ServerError;
class ConnectionError extends Error {
}
exports.ConnectionError = ConnectionError;
