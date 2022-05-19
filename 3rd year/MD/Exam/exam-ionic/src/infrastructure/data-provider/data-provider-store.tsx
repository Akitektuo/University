import { WS_URL } from "accessor/constants";
import { ReceivedItemModel } from "accessor/types";
import { makeAutoObservable } from "mobx";
import { createContext } from "react";

export class DataProviderStore {
    private isInitialized = false;

    constructor() {
        makeAutoObservable(this);
    }

    initialize = () => {
        if (this.isInitialized) {
            return () => {};
        }

        this.isInitialized = true;
        
        console.log(this.isInitialized);

        return this.buildWebSocket;
    }

    private buildWebSocket = () => {
        const webSocket = new WebSocket(WS_URL);

        webSocket.onmessage = ({ data }: MessageEvent<ReceivedItemModel[]>) => {
            console.log(data);
        }

        return webSocket.close;
    }
}

export const dataProviderStore = new DataProviderStore();
export const DataProviderContext = createContext(dataProviderStore);