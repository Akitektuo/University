import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { CookieService } from "./cookie.service";
import { AppService } from "./app.service";

@Component({
    template: "",
	providers: [AppService]
})
export class RequireAuthenticationComponent implements OnInit {
    constructor(protected router: Router,
		protected route: ActivatedRoute,
		protected cookies: CookieService,
		protected service: AppService) { }

    ngOnInit(): void {
        if (!this.cookies.isUserName()) {
            this.router.navigate(["../.."]);
            return;
        }

        this.onInit();
    }

    onInit(): void {

    }
    
	onIdParam(idParamChange: (number) => void) {
		this.route.params.subscribe(params =>
			idParamChange(params["id"]));
	}
}