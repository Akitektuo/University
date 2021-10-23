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
    // TODO: Register non-server write
    return OfflineCarAccessor.addCar(car);
}

export const updateCar = async (car: Car) => {
    if (isOnline()) {
        return OnlineCarAccessor.updateCar(car);
    }
    // TODO: Register non-server write
    return OfflineCarAccessor.updateCar(car);
}

export const deleteCar = (carId: number) => {
    if (isOnline()) {
        return OnlineCarAccessor.deleteCar(carId);
    }
    // TODO: Register non-server write
    return OfflineCarAccessor.deleteCar(carId);
}

const isOnline = () => networkStatusStore.isConnected;