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
        await OnlineCarAccessor.addCar(car);
        return true;
    }
    await SyncStorage.queueCreate(car);
    await OfflineCarAccessor.addCar(car);
    return false;
}

export const updateCar = async (car: Car) => {
    if (isOnline()) {
        await OnlineCarAccessor.updateCar(car);
        return true;
    }
    await SyncStorage.queueUpdate(car);
    await OfflineCarAccessor.updateCar(car);
    return false;
}

export const deleteCar = async (carId: number) => {
    if (isOnline()) {
        await OnlineCarAccessor.deleteCar(carId);
        return true;
    }
    await SyncStorage.queueDelete(carId);
    await OfflineCarAccessor.deleteCar(carId);
    return false;
}

export const syncChanges = async () => {
    const changes = await SyncStorage.getChanges();
    if (!changes?.length) {
        return;
    }

    const idMapping = await OnlineCarAccessor.syncChanges(changes);
    await OfflineCarAccessor.updateIds(idMapping);
}

const isOnline = () => networkStatusStore.isConnected;