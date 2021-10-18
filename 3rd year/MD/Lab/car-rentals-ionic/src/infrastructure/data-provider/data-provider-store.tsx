import { makeAutoObservable, runInAction } from "mobx";
import { createContext } from "react";
import { BuildWebSocket, toastServiceStore } from "..";
import { addCar, deleteCar, getAvailableCars, updateCar } from "../../accessors/car-accessor";
import { Car } from "../../accessors/types";
import { addToList, removeFromList, updateInList } from "../../shared/array-helpers";

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
        return this.getCars();
    }

    private getCars = () => {
        this.getAvailableCars();
        this.getRelatedCars();
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

    private getAvailableCars = async () => {
        const availableCars = await getAvailableCars();
        runInAction(() => {
            this.availableCars = availableCars;
        });
    }

    private getRelatedCars = () => {
        
    }

    private handleCreateChange = (car: Car) => {
        this.availableCars = addToList(this.availableCars, car);

        toastServiceStore.showInfo(<>
            New car (<strong>{car.brand} {car.model}</strong>) added to the list
        </>);
    }

    private handleUpdateChange = (car: Car) => {
        const [updatedList, carToUpdate] =
            updateInList(this.availableCars, car, ({ id }) => car.id === id);
        if (!carToUpdate) {
            return;
        }

        this.availableCars = updatedList;

        let newBrandOrModel = <></>;
        if (carToUpdate.brand !== car.brand || carToUpdate.model !== car.model) {
            newBrandOrModel = <>&nbsp;to&nbsp;<strong>{car.brand} {car.model}</strong></>;
        }

        toastServiceStore.showInfo(<>
            The car&nbsp;<strong>{carToUpdate.brand} {carToUpdate.model}</strong>&nbsp;was updated{newBrandOrModel}
        </>);
    }

    private handleDeleteChange = (car: Car) => {
        this.availableCars = removeFromList(this.availableCars, ({ id }) => car.id === id);

        toastServiceStore.showInfo(<>
            The car&nbsp;<strong>{car.brand} {car.model}</strong>&nbsp;was removed from the list
        </>);
    }
}

export const dataProviderStore = new DataProviderStore();
export const DataProviderContext = createContext(dataProviderStore);