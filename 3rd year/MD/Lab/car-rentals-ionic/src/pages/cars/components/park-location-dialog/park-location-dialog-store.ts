import { createContext } from "react";
import { getParkLocation, setParkLocation } from "../../../../accessors/online/park-location-accessor";
import { ParkLocation } from "../../../../accessors/types";
import { Geolocation } from "@capacitor/geolocation";
import { makeAutoObservable, runInAction } from "mobx";

export class ParkLocationDialogStore {
    public parkLocation: ParkLocation | null = null;

    constructor() {
        makeAutoObservable(this);
    }

    public loadParkLocation = async () => {
        const parkLocation = await getParkLocation();
        runInAction(() => this.parkLocation = parkLocation);
    }

    public setParkLocation = async () => {
        const { coords: { latitude, longitude } } = await Geolocation.getCurrentPosition();
        const parkLocation = {
            latitude,
            longitude
        };
        await setParkLocation(parkLocation);
        runInAction(() => this.parkLocation = parkLocation);
    }

    public reset = () => {
        this.parkLocation = null;
    }
}

export const parkLocationDialogStore = new ParkLocationDialogStore();
export const ParkLocationDialogContext = createContext(parkLocationDialogStore);