import { createContext } from "react";
import { action, observable, runInAction } from "mobx";
import { EMPTY_LOGIN_USER, LoginUser } from "../../../accessors/types";
import { login } from "../../../accessors/account-accessor";

export class LoginStore {
    @observable public user: LoginUser = EMPTY_LOGIN_USER;
    @observable public isLoading = false;
    @observable public errorMessage = "";

    @action
    public setEmail = (email: string) => this.user.email = email;

    @action
    public setPassword = (password: string) => this.user.password = password;

    @action
    public login = async () => {
        this.isLoading = true;
        let error = "";
        
        try {
            await login(this.user);
        } catch (exception: any) {
            error = exception.message;
        } finally {
            runInAction(() => {
                this.errorMessage = error;
                this.isLoading = false;
            });
        }
    }
}

export const loginStore = new LoginStore();
export const LoginContext = createContext(loginStore);