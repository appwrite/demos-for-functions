"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const compat_1 = require("./compat");
const ResultMeta_1 = require("./ResultMeta");
const ignore = () => { };
const intify = (v) => parseInt(v, 10);
class Result extends ResultMeta_1.default {
    /** @internal */
    constructor(meta, data) {
        super(meta);
        this._data = data;
    }
    /** @internal */
    meta() {
        /* Ignore errors on data, because they'll be propagated to meta too. */
        return this._data.catch(ignore) && this._meta;
    }
    /** @internal */
    data() {
        /* Ignore errors on meta, because they'll be propagated to data too. */
        return this._meta.catch(ignore) && this._data;
    }
    toFile(path, callback) {
        const writer = compat_1.writeFile.bind(null, path);
        return compat_1.nodeify(this.data().then(writer), callback);
    }
    toBuffer(callback) {
        return compat_1.nodeify(this.data(), callback);
    }
    size(callback) {
        return compat_1.nodeify(this.meta().then(meta => intify(meta["content-length"])), callback);
    }
    mediaType(callback) {
        return compat_1.nodeify(this.meta().then(meta => meta["content-type"]), callback);
    }
    contentType(callback) {
        return this.mediaType(callback);
    }
}
exports.default = Result;
