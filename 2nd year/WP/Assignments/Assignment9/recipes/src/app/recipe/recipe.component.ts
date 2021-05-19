import { Component } from "@angular/core";
import { EMPTY_RECIPE_WITH_DATA, RecipeWithData } from "../recipes.models";
import { RequireAuthenticationComponent } from "../require-authentication.component";

@Component({
	selector: "app-recipe",
	templateUrl: "./recipe.component.html",
	styleUrls: ["./recipe.component.scss"]
})
export class RecipeComponent extends RequireAuthenticationComponent {
	recipe: RecipeWithData = EMPTY_RECIPE_WITH_DATA;
	isCurrentUser: boolean = false;

	onInit(): void {
		this.getRecipe();
	}

	async getRecipe() {
		this.onIdParam(async id => {
			this.recipe = await this.service.getRecipeWithData(id);
			this.isCurrentUser = this.recipe.userId === this.cookies.getUserId();
		});
	}

	async onDelete() {
		await this.service.deleteRecipe(this.recipe.id);
		this.router.navigate(["../../recipes", this.recipe.typeId]);
	}

}
