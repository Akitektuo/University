import { Component } from "@angular/core";
import { EMPTY_RECIPE, Recipe, Type } from "../recipes.models";
import { RequireAuthenticationComponent } from "../require-authentication.component";

@Component({
	selector: "app-add-recipe",
	templateUrl: "./add-recipe.component.html",
	styleUrls: ["./add-recipe.component.scss"]
})
export class AddRecipeComponent extends RequireAuthenticationComponent {
	recipe: Recipe = EMPTY_RECIPE;
	types: Type[] = [];

	onInit(): void {
		this.fetchTypes();
	}

	async fetchTypes() {
		this.types = await this.service.getTypes();
	}

	async onAdd() {
		await this.service.createRecipe(this.recipe);
		this.router.navigate(["../recipes", this.recipe.typeId]);
	}
}
