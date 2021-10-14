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
        const cameraPhoto = await Camera.getPhoto({
            resultType: CameraResultType.Base64,
            source: CameraSource.Camera,
            quality: 100
        });
        runInAction(() => {
            this.car.image = cameraPhoto.base64String ?? "";
        });
    }

    public saveCar = async () => {
        const api = this.isAdd ? addCar : updateCar;

        try {
            await api(this.car);
        } catch {
            toastServiceStore.showError("Something went wrong, the server could not save the car, try again!");
            return false;
        }
        
        toastServiceStore.showSuccess("Operation successful!");
        return true;
    }

    public deleteCar = async () => {
        try {
            await deleteCar(this.car.id);
        } catch {
            toastServiceStore.showError("Something went wrong, the server could not delete the car, try again!");
            return false;
        }

        toastServiceStore.showSuccess("Operation successful!");
        return true;
    }

    public setCloseConfirmation = (show: boolean) => this.showCloseConfirmation = show;

    public setDeleteConfirmation = (show: boolean) => this.showDeleteConfirmation = show;
}

export const carEditStore = new CarEditStore();
export const CarEditContext = createContext(carEditStore);