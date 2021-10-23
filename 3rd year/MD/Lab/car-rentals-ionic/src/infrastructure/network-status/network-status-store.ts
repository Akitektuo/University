import { makeAutoObservable } from "mobx";
import { createContext } from "react";
import { ConnectionStatus, Network } from "@capacitor/network";

export class NetworkStatusStore {
    public isConnected: boolean | null | undefined;
    public onConnectionChange: (isConnected: boolean) => void = () => {};

    constructor() {
        makeAutoObservable(this)
    }

    public initialize = async () => {
        if (this.isConnected !== undefined) {
            return;
        }
        this.isConnected = null;

        Network.addListener("networkStatusChange", status => {
            this.setIsConnected(status);
            this.onConnectionChange(!!this.isConnected);
        });
        this.setIsConnected(await Network.getStatus());
    }

    private setIsConnected = (status: ConnectionStatus) => this.isConnected = status.connected;

    public waitForInitialization = () => new Promise<void>(resolve => {
        while(this.isConnected === undefined);
        resolve();
    });
}

export const networkStatusStore = new NetworkStatusStore();
export const NetworkStatusContext = createContext(networkStatusStore);