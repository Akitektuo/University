import { Component } from "@angular/core";
import { EMPTY_RECIPE, Recipe, Type } from "../recipes.models";
import { RequireAuthenticationComponent } from "../require-authentication.component";

@Component({
	selector: "app-edit-recipe",
	templateUrl: "./edit-recipe.component.html",
	styleUrls: ["./edit-recipe.component.scss"]
})
export class EditRecipeComponent extends RequireAuthenticationComponent {
	recipe: Recipe = EMPTY_RECIPE;
	types: Type[] = [];

	onInit(): void {
		this.fetchTypes();
		this.getRecipe();
	}

	async fetchTypes() {
		this.types = await this.service.getTypes();
	}

	async getRecipe() {
		this.onIdParam(async id => {
			this.recipe = await this.service.getRecipe(id);
			
			if (this.recipe.userId !== this.cookies.getUserId()) {
				this.navigateToRecipe();
			}
		});
	}

	async onSave() {
		await this.service.updateRecipe(this.recipe);
		this.navigateToRecipe();
	}

	navigateToRecipe() {
		this.router.navigate(["../../recipe", this.recipe.id]);
	}
}
