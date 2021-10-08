import { Callback } from "./compat";
import ResultMeta from "./ResultMeta";
export default class Result extends ResultMeta {
    toFile(path: string): Promise<void>;
    toFile(path: string, callback: Callback): void;
    toBuffer(): Promise<Uint8Array>;
    toBuffer(callback: Callback<Uint8Array>): void;
    size(): Promise<number>;
    size(callback: Callback<number>): void;
    mediaType(): Promise<string | void>;
    mediaType(callback: Callback<string>): void;
    contentType(): Promise<string | void>;
    contentType(callback: Callback<string>): void;
}
