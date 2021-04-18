import { Component, OnInit } from "@angular/core";
import { Type } from "../recipes.models";
import { RecipesService } from "../recipes.service";

@Component({
	selector: "app-categories",
	templateUrl: "./categories.component.html",
	styleUrls: ["./categories.component.scss"],
	providers: [RecipesService]
})
export class CategoriesComponent implements OnInit {
	categories: Type[] = [];

	constructor(private service: RecipesService) { }

	ngOnInit(): void {
		this.fetchTypes();
	}

	async fetchTypes() {
		this.categories = await this.service.getTypes();
	}
}
