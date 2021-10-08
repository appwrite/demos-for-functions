import { Callback } from "./compat";
import Result from "./Result";
import ResultMeta from "./ResultMeta";
export default class Source {
    static fromFile(path: string): Source;
    static fromBuffer(data: string | Uint8Array): Source;
    static fromUrl(url: string): Source;
    preserve(options: string[]): Source;
    preserve(...options: string[]): Source;
    resize(options: object): Source;
    store(options: object): ResultMeta;
    result(): Result;
    toFile(path: string): Promise<void>;
    toFile(path: string, callback: Callback): void;
    toBuffer(): Promise<Uint8Array>;
    toBuffer(callback: Callback<Uint8Array>): void;
}
