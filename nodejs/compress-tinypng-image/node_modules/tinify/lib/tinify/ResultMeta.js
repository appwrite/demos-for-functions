"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const compat_1 = require("./compat");
const intify = (v) => parseInt(v, 10);
class ResultMeta {
    /** @internal */
    constructor(meta) {
        this._meta = meta;
    }
    /** @internal */
    meta() {
        return this._meta;
    }
    width(callback) {
        return compat_1.nodeify(this.meta().then(meta => intify(meta["image-width"])), callback);
    }
    height(callback) {
        return compat_1.nodeify(this.meta().then(meta => intify(meta["image-height"])), callback);
    }
    location(callback) {
        return compat_1.nodeify(this.meta().then(meta => meta["location"]), callback);
    }
}
exports.default = ResultMeta;
