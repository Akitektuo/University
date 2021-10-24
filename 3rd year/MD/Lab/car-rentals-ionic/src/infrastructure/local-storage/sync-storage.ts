import { LocalStorage } from "..";
import { Car, Change, ChangeType, EMPTY_CAR } from "../../accessors/types";

const SYNC_CARS_STORAGE_KEY = "sync_cars_storage";

const queueCreate = async (car: Car) => {
    const queue = await LocalStorage.get<Change[]>(SYNC_CARS_STORAGE_KEY) || [];
    
    queue.push({
        type: ChangeType.Create,
        payload: car
    });

    await LocalStorage.set(SYNC_CARS_STORAGE_KEY, queue);
}

const queueUpdate = async (car: Car) => {
    const queue = await LocalStorage.get<Change[]>(SYNC_CARS_STORAGE_KEY) || [];
    
    queue.push({
        type: ChangeType.Update,
        payload: car
    });

    await LocalStorage.set(SYNC_CARS_STORAGE_KEY, queue);
}

const queueDelete = async (carId: number) => {
    let queue = await LocalStorage.get<Change[]>(SYNC_CARS_STORAGE_KEY) || [];
    
    if (carId > 0) {
        queue.push({
            type: ChangeType.Delete,
            payload: {
                ...EMPTY_CAR,
                id: carId
            }
        })
    } else {
        queue = queue.filter(({ payload }) => payload.id !== carId);
    } 

    await LocalStorage.set(SYNC_CARS_STORAGE_KEY, queue);
}

const getChanges = async () => {
    const queue = await LocalStorage.get<Change[]>(SYNC_CARS_STORAGE_KEY) || [];
    await LocalStorage.remove(SYNC_CARS_STORAGE_KEY);

    return queue;
}

export default {
    queueCreate,
    queueUpdate,
    queueDelete,
    getChanges
};