import { Injectable } from "@angular/core";
import { CookieService as CookieManager } from "ngx-cookie-service";

const KEY_USER_NAME = "userName";

@Injectable()
export class CookieService {
    constructor(private cookies: CookieManager) {}

    setUserName(userName: string) {
        this.cookies.set(KEY_USER_NAME, userName);
    }

    getUserName() {
        return this.cookies.get(KEY_USER_NAME);
    }

    isUserName() {
        return !!this.cookies.get(KEY_USER_NAME);
    }

    clearUserName() {
        this.cookies.delete(KEY_USER_NAME);
    }
}