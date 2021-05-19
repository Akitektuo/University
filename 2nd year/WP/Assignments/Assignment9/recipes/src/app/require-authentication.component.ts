import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { CookieService } from "./cookie.service";
import { RecipesService } from "./recipes.service";

@Component({
    template: "",
	providers: [RecipesService]
})
export class RequireAuthenticationComponent implements OnInit {
    constructor(protected router: Router,
		protected route: ActivatedRoute,
		protected cookies: CookieService,
		protected service: RecipesService) { }

    ngOnInit(): void {
        if (!this.cookies.isUserId()) {
            this.router.navigate(["../../login"]);
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