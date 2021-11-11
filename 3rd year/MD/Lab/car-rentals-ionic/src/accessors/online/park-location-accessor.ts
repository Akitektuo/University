import { ParkLocation } from "../types";
import { API_PATH_PARK_LOCATION, BASE_HTTP_URL } from "./constants";
import { httpGet, httpPost } from "./helper-functions";

const BASE_PARK_LOCATION_URL = BASE_HTTP_URL + API_PATH_PARK_LOCATION;

export const getParkLocation = async () => {
    try {
        return await httpGet<ParkLocation>(BASE_PARK_LOCATION_URL);
    } catch {
        return null;
    }
}

export const setParkLocation = (parkLocation: ParkLocation) =>
    httpPost(BASE_PARK_LOCATION_URL, parkLocation);