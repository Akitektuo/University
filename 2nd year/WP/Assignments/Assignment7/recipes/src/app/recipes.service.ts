import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Recipe, RecipeWithCategory, Type } from "./recipes.models";

@Injectable()
export class RecipesService {
    private BASE_URL = `http://localhost:80/api/recipes/`;

    constructor(private http: HttpClient) {
    }

    async getTypes() {
        return await this.http.get<Type[]>(`${this.BASE_URL}types.php`).toPromise();
    }

    async getRecipesByType(typeId: number) {
        return await this.http.get<RecipeWithCategory[]>(`${this.BASE_URL}recipes.php?type=${typeId}`).toPromise();
    }

    async getRecipe(id: number) {
        const recipes = await this.http.get<RecipeWithCategory[]>(`${this.BASE_URL}recipes.php?id=${id}`).toPromise();

        return recipes[0];
    }

    async deleteRecipe(id: number) {
        await this.http.delete(`${this.BASE_URL}recipes.php?id=${id}`).toPromise();
    }

    async createRecipe(recipe: Recipe) {
        await this.http.post(`${this.BASE_URL}recipes.php`, recipe).toPromise();
    }

    async updateRecipe(recipe: Recipe) {
        await this.http.put(`${this.BASE_URL}recipes.php`, recipe).toPromise();
    }
}