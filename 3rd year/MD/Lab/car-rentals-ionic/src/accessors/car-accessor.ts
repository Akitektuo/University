import { API_PATH_CARS, BASE_HTTP_URL } from "./constants";
import { httpDelete, httpGet, httpPost, httpPut } from "./helper-functions";
import { Car } from "./types";

const BASE_CAR_URL = BASE_HTTP_URL + API_PATH_CARS;

export const getAvailableCars = () => httpGet<Car[]>(BASE_CAR_URL);

export const addCar = (car: Car) => httpPost(BASE_CAR_URL, car);

export const updateCar = (car: Car) => httpPut(BASE_CAR_URL, car);

export const deleteCar = (carId: number) => httpDelete(`${BASE_CAR_URL}/${carId}`);