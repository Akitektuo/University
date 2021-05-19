import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { CookieService } from "../cookie.service";
import { User, EMPTY_USER } from "../recipes.models";
import { RecipesService } from "../recipes.service";

@Component({
    selector: "app-signup",
    templateUrl: "./signup.component.html",
    styleUrls: ["./signup.component.scss"]
})
export class SignupComponent implements OnInit {
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

    async createAccount() {
        try {
            await this.service.signup(this.user);
            this.router.navigate(["../login"]);
        } catch {
            alert("An error occurred, try again");
        }
    }
}