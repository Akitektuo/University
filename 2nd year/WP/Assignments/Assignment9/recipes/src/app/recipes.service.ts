import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Recipe, RecipeWithData , Type } from "./recipes.models";

@Injectable()
export class RecipesService {
    private BASE_URL = `http://localhost:44366/api/`;

    constructor(private http: HttpClient) {
    }

    async getTypes() {
        return await this.http.get<Type[]>(`${this.BASE_URL}types`).toPromise();
    }

    async getRecipesByType(typeId: number) {
        return await this.http.get<RecipeWithData[]>(`${this.BASE_URL}recipes?typeId=${typeId}`).toPromise();
    }

    async getRecipe(id: number) {
        const recipe = await this.http.get<RecipeWithData>(`${this.BASE_URL}recipes/${id}`).toPromise();

        return recipe;
    }

    async deleteRecipe(id: number) {
        await this.http.delete(`${this.BASE_URL}recipes/${id}`).toPromise();
    }

    async createRecipe(recipe: Recipe) {
        await this.http.post(`${this.BASE_URL}recipes`, recipe).toPromise();
    }

    async updateRecipe(recipe: Recipe) {
        await this.http.put(`${this.BASE_URL}recipes`, recipe).toPromise();
    }
}