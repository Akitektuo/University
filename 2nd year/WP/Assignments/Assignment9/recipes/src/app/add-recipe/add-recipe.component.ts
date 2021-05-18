import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { EMPTY_RECIPE, Recipe, Type } from "../recipes.models";
import { RecipesService } from "../recipes.service";

@Component({
	selector: "app-add-recipe",
	templateUrl: "./add-recipe.component.html",
	styleUrls: ["./add-recipe.component.scss"],
	providers: [RecipesService]
})
export class AddRecipeComponent implements OnInit {
	recipe: Recipe;
	types: Type[] = [];

	constructor(private router: Router, private service: RecipesService) { }

	ngOnInit(): void {
		this.recipe = EMPTY_RECIPE;
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
