import { Component, OnInit } from "@angular/core";
import { ActivatedRoute } from "@angular/router";
import { CookieService } from "ngx-cookie-service";
import { RecipeWithCategory } from "../recipes.models";
import { RecipesService } from "../recipes.service";

@Component({
	selector: "app-recipes",
	templateUrl: "./recipes.component.html",
	styleUrls: ["./recipes.component.scss"],
	providers: [RecipesService]
})
export class RecipesComponent implements OnInit {
	recipes: RecipeWithCategory[] = [];
	categoryName = "";
	pastCategoryName = "";

	constructor(private route: ActivatedRoute,
		private service: RecipesService,
		private cookies: CookieService) { }

	ngOnInit(): void {
		this.getRecipes();
	}

	onIdParam(idParamChange: (number) => void) {
		this.route.params.subscribe(params =>
			idParamChange(params["id"]));
	}

	getRecipes() {
		this.onIdParam(async id => {
			this.recipes = await this.service.getRecipesByType(id);
			this.categoryName = this.recipes[0]?.typeName;
			this.pastCategoryName = this.cookies.get("categoryName");
			this.cookies.set("categoryName", this.categoryName);
		});
	}
}
