import { makeAutoObservable, runInAction, toJS } from "mobx";
import { createContext } from "react";
import { Car, EMPTY_CAR } from "../../../../accessors/types";
import {
    Camera,
    CameraResultType,
    CameraSource
  } from "@capacitor/camera";
import { addCar, deleteCar, updateCar } from "../../../../accessors/car-accessor";
import { toastServiceStore } from "../../../../infrastructure";
import { dataProviderStore } from "../../../../infrastructure/data-provider/data-provider-store";

export class CarEditStore {
    public car: Car = EMPTY_CAR;
    public isAdd: boolean = false;
    public showCloseConfirmation: boolean = false;
    public showDeleteConfirmation: boolean = false;

    constructor() {
        makeAutoObservable(this);
    }

    public initializeCar = (initialCar: Car | null) => {
        if (!initialCar) {
            return;
        }

        if (initialCar.id === 0) {
            this.isAdd = true;
            this.car = EMPTY_CAR;
            return;
        }

        this.isAdd = false;
        this.car = toJS(initialCar);
    }

    public setBrand = (brand: string) => this.car.brand = brand;

    public setModel = (model: string) => this.car.model = model;

    public setFabricationYear = (fabricationYear: number | string) =>
        this.car.fabricationYear = fabricationYear === "" ? 0 : Number(fabricationYear);

    public setColor = (color: string) => this.car.color = color;

    public setAutomatic = (isAutomatic: boolean) => this.car.isAutomatic = isAutomatic;

    public canSave = () => this.car.brand && this.car.model &&
        this.car.fabricationYear && this.car.color && this.car.image;

    public takePicture = async () => {
        try {
            const cameraPhoto = await Camera.getPhoto({
                resultType: CameraResultType.Base64,
                source: CameraSource.Camera,
                quality: 100
            });
            runInAction(() => {
                this.car.image = cameraPhoto.base64String ?? "";
            });
        } catch {}
    }

    public pickPicture = async () => {
        try {
            const cameraPhoto = await Camera.getPhoto({
                resultType: CameraResultType.Base64,
                source: CameraSource.Photos,
                quality: 100
            });
            runInAction(() => {
                this.car.image = cameraPhoto.base64String ?? "";
            });
        } catch {}
}

    public saveCar = async () => {
        const api = this.isAdd ? addCar : updateCar;

        try {
            const online = await api(this.car);

            if (online) {
                toastServiceStore.showSuccess("Operation successful!");
            } else {
                const saveMethod = this.isAdd ?
                    dataProviderStore.addToRelatedCars :
                    dataProviderStore.updateInRelatedCars;
                
                saveMethod(this.car);

                toastServiceStore.showWarning("Car saved to local storage!");
            }
        } catch {
            toastServiceStore.showError("Something went wrong, the server could not save the car, try again!");
            return false;
        }
        return true;
    }

    public deleteCar = async () => {
        try {
            const online = await deleteCar(this.car.id);

            if (online) {
                toastServiceStore.showSuccess("Operation successful!");
            } else {
                dataProviderStore.deleteFromRelatedCars(this.car);
                toastServiceStore.showWarning("Car removed from local storage!");
            }
        } catch {
            toastServiceStore.showError("Something went wrong, the server could not delete the car, try again!");
            return false;
        }
        return true;
    }

    public setCloseConfirmation = (show: boolean) => this.showCloseConfirmation = show;

    public setDeleteConfirmation = (show: boolean) => this.showDeleteConfirmation = show;
}

export const carEditStore = new CarEditStore();
export const CarEditContext = createContext(carEditStore);