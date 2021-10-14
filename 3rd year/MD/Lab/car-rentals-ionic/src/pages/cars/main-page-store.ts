import { makeAutoObservable, runInAction } from "mobx";
import { createContext } from "react";
import { Car, EMPTY_CAR } from "../../accessors/types";

export class MainPageStore {
    public selectedTab: number = 0;
    public carToEdit: Car | null = null;

    constructor() {
        makeAutoObservable(this);
    }

    public setSelectedTab = (tabIndex: number) => this.selectedTab = tabIndex;

    public showAddDialog = () => this.carToEdit = EMPTY_CAR;

    public closeDialog = () => this.carToEdit = null;

    public showEditDialog = (car: Car) => this.carToEdit = car;
}

export const mainPageStore = new MainPageStore();
export const MainPageContext = createContext(mainPageStore);