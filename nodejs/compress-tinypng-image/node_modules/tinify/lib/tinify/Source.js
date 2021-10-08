"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const tinify_1 = require("../tinify");
const compat_1 = require("./compat");
class Source {
    /** @internal */
    constructor(url, commands) {
        this._url = url;
        this._commands = commands || {};
    }
    static fromFile(path) {
        const location = compat_1.readFile(path).then(data => {
            const response = tinify_1.default.client.request("post", "/shrink", data);
            return response.then(res => res.headers.location);
        });
        return new tinify_1.default.Source(location);
    }
    static fromBuffer(data) {
        const response = tinify_1.default.client.request("post", "/shrink", data);
        const location = response.then(res => res.headers.location);
        return new tinify_1.default.Source(location);
    }
    static fromUrl(url) {
        const response = tinify_1.default.client.request("post", "/shrink", { source: { url } });
        const location = response.then(res => res.headers.location);
        return new tinify_1.default.Source(location);
    }
    preserve(...options) {
        if (Array.isArray(options[0]))
            options = options[0];
        return new tinify_1.default.Source(this._url, Object.assign({ preserve: options }, this._commands));
    }
    resize(options) {
        return new tinify_1.default.Source(this._url, Object.assign({ resize: options }, this._commands));
    }
    store(options) {
        const commands = Object.assign({ store: options }, this._commands);
        const response = this._url.then(url => {
            return tinify_1.default.client.request("post", url, commands);
        });
        return new tinify_1.default.ResultMeta(response.then(res => res.headers));
    }
    result() {
        const commands = this._commands;
        const response = this._url.then(url => {
            return tinify_1.default.client.request("get", url, commands);
        });
        return new tinify_1.default.Result(response.then(res => res.headers), response.then(res => res.body));
    }
    toFile(path, callback) {
        return this.result().toFile(path, callback);
    }
    toBuffer(callback) {
        return this.result().toBuffer(callback);
    }
}
exports.default = Source;
