import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { CookieService } from "../cookie.service";
import { EMPTY_USER, User } from "../recipes.models";
import { RecipesService } from "../recipes.service";

@Component({
    selector: "app-login",
    templateUrl: "./login.component.html",
    styleUrls: ["./login.component.scss"]
})
export class LoginComponent implements OnInit {
    user: User = EMPTY_USER;

    constructor(private router: Router,
        private service: RecipesService,
        private cookies: CookieService) { }

    ngOnInit(): void {
        if (this.cookies.isUserId()) {
            this.router.navigate([".."]);
            return;
        }

        this.user = EMPTY_USER;
    }

    async onLogin() {
        try {
            this.cookies.setUserId(await this.service.login(this.user));
            this.router.navigate([".."]);
        } catch {
            alert("An error occurred, try again");
        }
    }
}