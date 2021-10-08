"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const fs = require("fs");
/** @internal */
function promisify(fn) {
    return function () {
        const args = Array.from(arguments);
        return new Promise((resolve, reject) => {
            args.push((err, value) => {
                if (err)
                    return reject(err);
                resolve(value);
            });
            fn.apply(undefined, args);
        });
    };
}
/** @internal */
exports.readFile = promisify(fs.readFile);
/** @internal */
exports.writeFile = promisify(fs.writeFile);
/** @internal */
const nodeify = require("promise-nodeify");
exports.nodeify = nodeify;
