import { makeAutoObservable, runInAction } from "mobx";
import { createContext } from "react";
import { authorizedStore, BuildWebSocket, networkStatusStore, toastServiceStore } from "..";
import OfflineCarAccessor from "../../accessors/offline/car-accessor";
import {
    addCar,
    deleteCar,
    getAvailableCars,
    getRelatedCars,
    syncChanges,
    updateCar
} from "../../accessors/car-accessor";
import { Car } from "../../accessors/types";
import { addToList, removeFromList, updateInList } from "../../shared/helpers/array-helpers";

export class DataProviderStore {
    public availableCars: Car[] = []
    public relatedCars: Car[] = []
    private isInitialized = false
    private unsubscribe = () => {};

    constructor() {
        makeAutoObservable(this);
    }

    public addCar = async (car: Car) => addCar(car);

    public updateCar = async (car: Car) => updateCar(car);

    public deleteCar = (car: Car) => deleteCar(car.id);

    initialize = () => {
        if (this.isInitialized) {
            return () => {};
        }

        this.isInitialized = true;
        
        networkStatusStore.onConnectionChange = connected => {
            if (connected)
                syncChanges();
        }

        this.getCars();
        return this.subscribeToChanges();
    }

    private subscribeToChanges = async () => {
        const unsubscribe = await BuildWebSocket()
            .onCreate(this.handleCreateChange)
            .onUpdate(this.handleUpdateChange)
            .onDelete(this.handleDeleteChange)
            .connect();

        runInAction(() => this.unsubscribe = unsubscribe);
    }

    public unsubscribeToChanges = () => {
        try {
            this.unsubscribe();
        } catch {}
    }

    private getCars = () => setTimeout(() => {
        this.getAvailableCars();
        this.getRelatedCars();
    }, 100);

    private getAvailableCars = async () => {
        const availableCars = await getAvailableCars();
        runInAction(() => {
            this.availableCars = availableCars;
        });
    }

    private getRelatedCars = async () => {
        const relatedCars = await getRelatedCars();
        runInAction(() => {
            this.relatedCars = relatedCars;
        });
    }

    private handleCreateChange = (car: Car) => {
        if (car.userId === authorizedStore.userId) {
            this.relatedCars = addToList(this.relatedCars, car);
            OfflineCarAccessor.addCar(car);
        } else {
            this.availableCars = addToList(this.availableCars, car);
            OfflineCarAccessor.setAvailableCars(this.availableCars);
        }

        toastServiceStore.showInfo(<>
            New car (<strong>{car.brand} {car.model}</strong>) added to the list
        </>);
    }

    private handleUpdateChange = (car: Car) => {
        const isRelated = car.userId === authorizedStore.userId;
        const [updatedList, carToUpdate] = updateInList(
            isRelated ? this.relatedCars : this.availableCars,
            car,
            ({ id }) => car.id === id);
        if (!carToUpdate) {
            return;
        }

        if (isRelated) {
            this.relatedCars = updatedList;
            OfflineCarAccessor.updateCar(car);
        } else {
            this.availableCars = updatedList;
            OfflineCarAccessor.setAvailableCars(this.availableCars);
        }

        let newBrandOrModel = <></>;
        if (carToUpdate.brand !== car.brand || carToUpdate.model !== car.model) {
            newBrandOrModel = <>&nbsp;to&nbsp;<strong>{car.brand} {car.model}</strong></>;
        }

        toastServiceStore.showInfo(<>
            The car&nbsp;<strong>{carToUpdate.brand} {carToUpdate.model}</strong>&nbsp;was updated{newBrandOrModel}
        </>);
    }

    private handleDeleteChange = (car: Car) => {
        if (car.userId === authorizedStore.userId) {
            this.relatedCars = removeFromList(this.relatedCars, ({ id }) => car.id === id);
            OfflineCarAccessor.deleteCar(car.id);
        } else {
            this.availableCars = removeFromList(this.availableCars, ({ id }) => car.id === id);
            OfflineCarAccessor.setAvailableCars(this.availableCars);
        }

        toastServiceStore.showInfo(<>
            The car&nbsp;<strong>{car.brand} {car.model}</strong>&nbsp;was removed from the list
        </>);
    }
}

export const dataProviderStore = new DataProviderStore();
export const DataProviderContext = createContext(dataProviderStore);