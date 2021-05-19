import { Component } from "@angular/core";
import { RecipeWithData } from "../recipes.models";
import { RequireAuthenticationComponent } from "../require-authentication.component";

@Component({
	selector: "app-recipes",
	templateUrl: "./recipes.component.html",
	styleUrls: ["./recipes.component.scss"]
})
export class RecipesComponent extends RequireAuthenticationComponent {
	recipes: RecipeWithData[] = [];
	categoryName = "";
	pastCategoryName = "";

	onInit(): void {
		this.getRecipes();
	}

	getRecipes() {
		this.onIdParam(async id => {
			this.recipes = await this.service.getRecipesByType(id);
			this.categoryName = this.recipes[0]?.type;
			this.pastCategoryName = this.cookies.getCategoryName();
			this.cookies.setCategoryName(this.categoryName);
		});
	}
}
