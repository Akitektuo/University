import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddRecipeComponent } from './add-recipe/add-recipe.component';
import { CategoriesComponent } from './categories/categories.component';
import { EditRecipeComponent } from './edit-recipe/edit-recipe.component';
import { RecipeComponent } from './recipe/recipe.component';
import { RecipesComponent } from './recipes/recipes.component';

export const RoutingComponents = [
	CategoriesComponent,
	RecipesComponent,
	RecipeComponent,
	AddRecipeComponent,
	EditRecipeComponent
];

const routes: Routes = [
	{ path: "recipes/:id", component: RecipesComponent },
	{ path: "recipe/:id", component: RecipeComponent },
	{ path: "add-recipe", component: AddRecipeComponent },
	{ path: "edit-recipe/:id", component: EditRecipeComponent },
	{ path: "**", component: CategoriesComponent }
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
