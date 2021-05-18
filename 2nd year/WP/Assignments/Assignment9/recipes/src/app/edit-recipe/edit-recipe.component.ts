import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { Recipe, Type } from "../recipes.models";
import { RecipesService } from "../recipes.service";

@Component({
	selector: "app-edit-recipe",
	templateUrl: "./edit-recipe.component.html",
	styleUrls: ["./edit-recipe.component.scss"],
	providers: [RecipesService]
})
export class EditRecipeComponent implements OnInit {
	recipe: Recipe;
	types: Type[] = [];

	constructor(private router: Router,
		private route: ActivatedRoute,
		private service: RecipesService) { }

	ngOnInit(): void {
		this.fetchTypes();
		this.getRecipe();
	}

	onIdParam(idParamChange: (number) => void) {
		this.route.params.subscribe(params =>
			idParamChange(params["id"]));
	}

	async fetchTypes() {
		this.types = await this.service.getTypes();
	}

	async getRecipe() {
		this.onIdParam(async id => this.recipe = await this.service.getRecipe(id));
	}

	async onSave() {
		await this.service.updateRecipe(this.recipe);
		this.router.navigate(["../../recipe", this.recipe.id]);
	}
}
