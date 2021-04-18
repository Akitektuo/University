import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, Router } from "@angular/router";
import { RecipeWithCategory } from "../recipes.models";
import { RecipesService } from "../recipes.service";

@Component({
	selector: "app-recipe",
	templateUrl: "./recipe.component.html",
	styleUrls: ["./recipe.component.scss"],
	providers: [RecipesService]
})
export class RecipeComponent implements OnInit {
	recipe: RecipeWithCategory;

	constructor(private router: Router,
		private route: ActivatedRoute,
		private service: RecipesService) { }

	ngOnInit(): void {
		this.getRecipe();
	}

	onIdParam(idParamChange: (number) => void) {
		this.route.params.subscribe(params =>
			idParamChange(params["id"]));
	}

	async getRecipe() {
		this.onIdParam(async id => this.recipe = await this.service.getRecipe(id));
	}

	async onDelete() {
		await this.service.deleteRecipe(this.recipe.id);
		this.router.navigate(["../../recipes", this.recipe.typeId]);
	}

}
