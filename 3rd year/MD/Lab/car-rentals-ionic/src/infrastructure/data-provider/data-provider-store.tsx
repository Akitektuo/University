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

const PAGINATION_COUNT = 4;

export class DataProviderStore {
    public availableCars: Car[] = [];
    public relatedCars: Car[] = [];
    public disabledScroll: boolean = false;
    public search: string = "";
    public automaticFilter: boolean | null = null;
    private isInitialized = false;
    private unsubscribe = () => {};
    private start = 0;

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
        this.start = 0;
        this.relatedCars = [];
        this.disabledScroll = false;
        await this.fetchRelatedCars();
    }

    public fetchRelatedCars = async () => {
        const relatedCars = await getRelatedCars(
            this.search,
            this.automaticFilter,
            this.start,
            PAGINATION_COUNT);

        runInAction(() => {
            this.start += PAGINATION_COUNT;
            this.disabledScroll = relatedCars.length < PAGINATION_COUNT;
            this.relatedCars.push(...relatedCars);
            // this.relatedCars = [...this.relatedCars, ...relatedCars]
        });
    }

    public setSearch = (search: string) => {
        this.search = search;
        this.getRelatedCars();
    }

    public setAutomaticFilter = (isAutomatic: boolean | null) => {
        this.automaticFilter = isAutomatic;
        this.getRelatedCars();
    }

    private handleCreateChange = (car: Car) => {
        if (car.userId === authorizedStore.userId) {
            this.relatedCars = addToList(this.relatedCars, car);
            this.addToRelatedCars(car)
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
            this.deleteFromRelatedCars(car);
            OfflineCarAccessor.deleteCar(car.id);
        } else {
            this.availableCars = removeFromList(this.availableCars, ({ id }) => car.id === id);
            OfflineCarAccessor.setAvailableCars(this.availableCars);
        }

        toastServiceStore.showInfo(<>
            The car&nbsp;<strong>{car.brand} {car.model}</strong>&nbsp;was removed from the list
        </>);
    }

    public addToRelatedCars = (car: Car) => this.relatedCars = addToList(this.relatedCars, car);

    public updateInRelatedCars = (car: Car) => {
        const [updatedList, carToUpdate] =
            updateInList(this.relatedCars, car, ({ id }) => car.id === id);
        if (!carToUpdate) {
            return;
        }

        this.relatedCars = updatedList;
    }

    public deleteFromRelatedCars = (car: Car) =>
        this.relatedCars = removeFromList(this.relatedCars, ({ id }) => car.id === id);
}

export const dataProviderStore = new DataProviderStore();
export const DataProviderContext = createContext(dataProviderStore);