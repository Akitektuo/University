import { AvailableCarsStorage, RelatedCarsStorage } from "../../infrastructure";
import { Car, IdMap } from "../types";

const FIRST_TEMPORARY_ID = -1;

export const getAvailableCars = () => AvailableCarsStorage.get();

export const getRelatedCars = () => RelatedCarsStorage.get();

export const setAvailableCars = (cars: Car[]) => AvailableCarsStorage.set(cars);

export const setRelatedCars = async (cars: Car[]) => {
    const existingCars = await RelatedCarsStorage.get();

    await RelatedCarsStorage.set(merge(existingCars, cars));
}

export const addCar = async (car: Car) => {
    const cars = await RelatedCarsStorage.get();

    if (!car.id) {
        car.id = getTemporaryId(cars);
    }
    cars.push(car);

    await RelatedCarsStorage.set(cars);
}

export const updateCar = async (car: Car) => {
    const cars = await RelatedCarsStorage.get();

    const indexOfModifiedCar = getIndexOfCar(cars, car.id);
    cars[indexOfModifiedCar] = car;

    await RelatedCarsStorage.set(cars);
}

export const deleteCar = async (carId: number) => {
    const cars = await RelatedCarsStorage.get();

    const indexOfModifiedCar = getIndexOfCar(cars, carId);
    cars.splice(indexOfModifiedCar, 1);

    await RelatedCarsStorage.set(cars);
}

export const updateIds = async (idMapping: IdMap[]) => {
    const cars = await RelatedCarsStorage.get();

    idMapping.forEach(({ from, to }) => {
        let carIndexToUpdate = cars.findIndex(({ id }) => from === id);
        while (carIndexToUpdate > -1) {
            cars[carIndexToUpdate].id = to;
            carIndexToUpdate = cars.findIndex(({ id }) => from === id);
        }
    });

    await RelatedCarsStorage.set(cars);
}

const getTemporaryId = (carsAsReference: Car[]) => {
    const carIds = carsAsReference.map(car => car.id);
    const lastId = Math.min(...carIds);

    return lastId > FIRST_TEMPORARY_ID ? FIRST_TEMPORARY_ID : lastId - 1;
}

const getIndexOfCar = (fromCars: Car[], carId: number) =>
    fromCars.findIndex(car => car.id === carId);

const merge = (localCars: Car[], fetchedCars: Car[]) => {
    fetchedCars.forEach(car => {
        const existentCarIndex = localCars.findIndex(({ id }) => id === car.id);

        if (existentCarIndex > -1) {
            localCars[existentCarIndex] = car;
        } else {
            localCars.push(car);
        }
    });

    return localCars;
}

const OfflineCarAccessor = {
    getAvailableCars,
    getRelatedCars,
    setAvailableCars,
    setRelatedCars,
    addCar,
    updateCar,
    deleteCar,
    updateIds
}

export default OfflineCarAccessor;