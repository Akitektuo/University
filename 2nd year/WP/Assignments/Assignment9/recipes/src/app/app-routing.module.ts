import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AddRecipeComponent } from './add-recipe/add-recipe.component';
import { CategoriesComponent } from './categories/categories.component';
import { EditRecipeComponent } from './edit-recipe/edit-recipe.component';
import { LoginComponent } from './login/login.component';
import { RecipeComponent } from './recipe/recipe.component';
import { RecipesComponent } from './recipes/recipes.component';
import { SignupComponent } from './signup/signup.component';

export const RoutingComponents = [
	CategoriesComponent,
	RecipesComponent,
	RecipeComponent,
	AddRecipeComponent,
	EditRecipeComponent,
	LoginComponent,
	SignupComponent
];

const routes: Routes = [
	{ path: "recipes/:id", component: RecipesComponent },
	{ path: "recipe/:id", component: RecipeComponent },
	{ path: "add-recipe", component: AddRecipeComponent },
	{ path: "edit-recipe/:id", component: EditRecipeComponent },
	{ path: "login", component: LoginComponent },
	{ path: "signup", component: SignupComponent },
	{ path: "**", component: CategoriesComponent }
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
