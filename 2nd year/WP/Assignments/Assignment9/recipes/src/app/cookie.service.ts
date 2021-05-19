import { Injectable } from "@angular/core";
import { CookieService as CookieManager } from "ngx-cookie-service";

const KEY_CATEGORY_NAME = "categoryName";
const KEY_USER_ID = "userId";

@Injectable()
export class CookieService {
    constructor(private cookies: CookieManager) {}

    setCategoryName(categoryName: string) {
        this.cookies.set(KEY_CATEGORY_NAME, categoryName);
    }

    getCategoryName() {
        return this.cookies.get(KEY_CATEGORY_NAME);
    }

    setUserId(userId: number) {
        this.cookies.set(KEY_USER_ID, userId.toString());
    }

    getUserId() {
        return parseInt(this.cookies.get(KEY_USER_ID));
    }

    isUserId() {
        return !!this.cookies.get(KEY_USER_ID);
    }

    clearUserId() {
        this.cookies.delete(KEY_USER_ID);
    }
}