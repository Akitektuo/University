import { SyncStorage } from "../infrastructure";
import { networkStatusStore } from "../infrastructure/network-status/network-status-store";
import OfflineCarAccessor from "./offline/car-accessor";
import OnlineCarAccessor from "./online/car-accessor";
import { Car } from "./types";

export const getAvailableCars = async () => {
    if (!isOnline()) {
        return OfflineCarAccessor.getAvailableCars();
    }

    const cars = await OnlineCarAccessor.getAvailableCars();
    await OfflineCarAccessor.setAvailableCars(cars);
    return cars;
}

export const getRelatedCars = async () => {
    if (!isOnline()) {
        return OfflineCarAccessor.getRelatedCars();
    }

    const cars = await OnlineCarAccessor.getRelatedCars();
    await OfflineCarAccessor.setRelatedCars(cars);
    return cars;
}

export const addCar = async (car: Car) => {
    if (isOnline()) {
        return OnlineCarAccessor.addCar(car);
    }
    await SyncStorage.queueCreate(car);
    return OfflineCarAccessor.addCar(car);
}

export const updateCar = async (car: Car) => {
    if (isOnline()) {
        return OnlineCarAccessor.updateCar(car);
    }
    await SyncStorage.queueUpdate(car);
    return OfflineCarAccessor.updateCar(car);
}

export const deleteCar = async (carId: number) => {
    if (isOnline()) {
        return OnlineCarAccessor.deleteCar(carId);
    }
    await SyncStorage.queueDelete(carId);
    return OfflineCarAccessor.deleteCar(carId);
}

export const syncChanges = async () => {
    const changes = await SyncStorage.getChanges();
    if (!changes) {
        return;
    }

    const idMapping = await OnlineCarAccessor.syncChanges(changes);
    await OfflineCarAccessor.updateIds(idMapping);
}

const isOnline = () => networkStatusStore.isConnected;