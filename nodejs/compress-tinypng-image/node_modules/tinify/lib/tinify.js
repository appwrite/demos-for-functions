"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const compat_1 = require("./tinify/compat");
const Source_1 = require("./tinify/Source");
const Error_1 = require("./tinify/Error");
class Tinify {
    constructor() {
        this.default = this;
    }
    set key(key) {
        this._key = key;
        this._client = undefined;
    }
    set appIdentifier(appIdentifier) {
        this._appIdentifier = appIdentifier;
        this._client = undefined;
    }
    set proxy(proxy) {
        this._proxy = proxy;
        this._client = undefined;
    }
    get client() {
        if (!this._key) {
            throw new this.AccountError("Provide an API key with tinify.key = ...");
        }
        if (!this._client) {
            this._client = new this.Client(this._key, this._appIdentifier, this._proxy);
        }
        return this._client;
    }
    fromFile(path) {
        return Source_1.default.fromFile(path);
    }
    fromBuffer(data) {
        return Source_1.default.fromBuffer(data);
    }
    fromUrl(url) {
        return Source_1.default.fromUrl(url);
    }
    validate(callback) {
        function is429(err) {
            return err instanceof Error_1.AccountError && err.status === 429;
        }
        try {
            const request = this.client.request("post", "/shrink");
            return compat_1.nodeify(request.catch(err => {
                if (err instanceof Error_1.ClientError || is429(err))
                    return;
                throw err;
            }).then(function ignore() { }), callback);
        }
        catch (err) {
            return compat_1.nodeify(Promise.reject(err), callback);
        }
    }
}
exports.default = new Tinify;
