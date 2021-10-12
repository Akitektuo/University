export interface LoginUser {
    email: string;
    password: string;
}

export interface RegisterUser extends LoginUser {
    confirmPassword: string;
}

export interface LoginResponse {
    token: string;
    expiration: string;
}

export const EMPTY_LOGIN_USER: LoginUser = {
    email: "",
    password: ""
}

export const EMPTY_REGISTER_USER: RegisterUser = {
    ...EMPTY_LOGIN_USER,
    confirmPassword: ""
}

export interface Car {
    id: number;
    brand: string;
    model: string;
    fabricationYear: number;
    color: string;
    image: Uint8Array;
    isAutomatic: boolean;
}

export const EMPTY_CAR: Car = {
    id: 0,
    brand: "",
    model: "",
    fabricationYear: 0,
    color: "",
    image: new Uint8Array(),
    isAutomatic: false
}