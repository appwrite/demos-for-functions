import { Callback } from "./compat";
export default class ResultMeta {
    width(): Promise<number>;
    width(callback: Callback<number>): void;
    height(): Promise<number>;
    height(callback: Callback<number>): void;
    location(): Promise<string | void>;
    location(callback: Callback<string>): void;
}
