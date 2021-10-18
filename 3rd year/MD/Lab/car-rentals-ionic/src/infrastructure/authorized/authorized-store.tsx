import { makeAutoObservable, runInAction } from "mobx";
import { createContext } from "react";
import { AuthenticationStorage } from "..";

export class AuthorizedStore {
    public userId?: string | null;

    constructor() {
        makeAutoObservable(this);
    }

    public initialize = () => {
        if (this.userId === undefined) {
            this.checkAuthorization();
        }
    }

    public isAuthorized = () => this.userId === undefined ? undefined : !!this.userId;

    public checkAuthorization = async () => {
        const result = await AuthenticationStorage.get();

        runInAction(() => this.userId = result && result.userId);
    }
}

export const authorizedStore = new AuthorizedStore();
export const AuthorizedContext = createContext(authorizedStore); 