import { makeAutoObservable } from "mobx";
import { createContext } from "react";

export class LoadingOverlayStore {
    public activeLoadings: number = 0;

    constructor() {
        makeAutoObservable(this);
    }

    public setLoading = (show: boolean) => {
        if (show)
            return ++this.activeLoadings;

        --this.activeLoadings;
    }
}

export const loadingOverlayStore = new LoadingOverlayStore();
export const LoadingOverlayContext = createContext(loadingOverlayStore);