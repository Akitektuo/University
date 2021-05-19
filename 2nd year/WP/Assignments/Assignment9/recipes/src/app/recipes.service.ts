import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { CookieService } from "./cookie.service";
import { Recipe, RecipeWithData , Type, User } from "./recipes.models";

@Injectable()
export class RecipesService {
    private BASE_URL = `https://localhost:44366/api/`;

    constructor(private http: HttpClient, private cookies: CookieService) {
    }

    async getTypes() {
        return await this.http.get<Type[]>(`${this.BASE_URL}types`).toPromise();
    }

    async getRecipesByType(typeId: number) {
        return await this.http.get<RecipeWithData[]>(`${this.BASE_URL}recipes?typeId=${typeId}`).toPromise();
    }

    async getRecipeWithData(id: number) {
        const recipe = await this.http.get<RecipeWithData>(`${this.BASE_URL}recipes/${id}`).toPromise();

        return recipe;
    }

    async getRecipe(id: number): Promise<Recipe> {
        const recipe = await this.http.get<RecipeWithData>(`${this.BASE_URL}recipes/${id}`).toPromise();

        delete recipe.type;
        delete recipe.user;

        return recipe;
    }

    async deleteRecipe(id: number) {
        await this.http.delete(`${this.BASE_URL}recipes/${id}`).toPromise();
    }

    async createRecipe(recipe: Recipe) {
        recipe.userId = this.cookies.getUserId();

        await this.http.post(`${this.BASE_URL}recipes`, recipe).toPromise();
    }

    async updateRecipe(recipe: Recipe) {
        await this.http.put(`${this.BASE_URL}recipes`, recipe).toPromise();
    }

    async login(user: User) {
        return await this.http.post<number>(`${this.BASE_URL}users/login`, user).toPromise();
    }

    async signup(user: User) {
        return await this.http.post<number>(`${this.BASE_URL}users/register`, user).toPromise();
    }
}