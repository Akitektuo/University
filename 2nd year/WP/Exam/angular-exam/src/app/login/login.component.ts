import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { CookieService } from "../cookie.service";

@Component({
    selector: "app-login",
    templateUrl: "./login.component.html",
    styleUrls: ["./login.component.scss"]
})
export class LoginComponent implements OnInit {
    username: string = "";

    constructor(private router: Router,
        private cookies: CookieService) { }

    ngOnInit(): void {
        if (this.cookies.isUserName()) {
            this.router.navigate(["/teams"]);
            return;
        }

        this.username = "";
    }

    async onLogin() {
        this.cookies.setUserName(this.username);
        this.router.navigate(["/teams"]);
    }
}