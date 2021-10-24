import { API_PATH_CARS, BASE_HTTP_URL } from "./constants";
import { httpDelete, httpGet, httpPost, httpPut } from "./helper-functions";
import { Car, Change, IdMap } from "../types";

const BASE_CAR_URL = BASE_HTTP_URL + API_PATH_CARS;

export const getAvailableCars = () => httpGet<Car[]>(`${BASE_CAR_URL}/available`);

export const getRelatedCars = () => httpGet<Car[]>(`${BASE_CAR_URL}/related`);

export const addCar = (car: Car) => httpPost(BASE_CAR_URL, car);

export const updateCar = (car: Car) => httpPut(BASE_CAR_URL, car);

export const deleteCar = (carId: number) => httpDelete(`${BASE_CAR_URL}/${carId}`);

export const syncChanges = (changes: Change[]) =>
    httpPost<IdMap[]>(`${BASE_CAR_URL}/sync`, changes);

const OnlineCarAccessor = {
    getAvailableCars,
    getRelatedCars,
    addCar,
    updateCar,
    deleteCar,
    syncChanges
}

export default OnlineCarAccessor;