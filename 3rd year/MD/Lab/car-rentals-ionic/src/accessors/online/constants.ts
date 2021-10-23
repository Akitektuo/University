export const HTTP_PROTOCOL = "https";
export const WEB_SOCKET_PROTOCOL = "wss";

export const BASE_URL = "localhost";
export const PORT = 44325;

export const API_PATH_AUTHENTICATE = "authenticate";
export const API_PATH_CARS = "cars";

export const BASE_URL_WITHOUT_PROTOCOL = `://${BASE_URL}:${PORT}/api/`;

export const BASE_HTTP_URL = HTTP_PROTOCOL + BASE_URL_WITHOUT_PROTOCOL;
export const BASE_WS_URL = WEB_SOCKET_PROTOCOL + BASE_URL_WITHOUT_PROTOCOL;