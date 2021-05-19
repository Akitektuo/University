import { Component } from "@angular/core";
import { Type } from "../recipes.models";
import { RequireAuthenticationComponent } from "../require-authentication.component";

@Component({
	selector: "app-categories",
	templateUrl: "./categories.component.html",
	styleUrls: ["./categories.component.scss"]
})
export class CategoriesComponent extends RequireAuthenticationComponent {
	categories: Type[] = [];

	onInit(): void {
		this.fetchTypes();
	}

	async fetchTypes() {
		this.categories = await this.service.getTypes();
	}

	onSignOut() {
		this.cookies.clearUserId();
		this.router.navigate(["login"]);
	}
}
