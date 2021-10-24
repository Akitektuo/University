import { LocalStorage } from "..";
import { Car } from "../../accessors/types";

export const CarsStorage = (key: string) => ({
    set: (cars: Car[]) => LocalStorage.set<Car[]>(key, cars),
    get: async () => await LocalStorage.get<Car[]>(key) || [],
    clear: () => LocalStorage.remove(key),
});

const AVAILABLE_CARS_STORAGE_KEY = "available_cars_storage";
const RELATED_CARS_STORAGE_KEY = "related_cars_storage";

export const AvailableCarsStorage = CarsStorage(AVAILABLE_CARS_STORAGE_KEY);

export const RelatedCarsStorage = CarsStorage(RELATED_CARS_STORAGE_KEY);