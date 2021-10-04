import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { TeamsComponent } from './teams/teams.component';

export const RoutingComponents = [
	LoginComponent,
	TeamsComponent
];

const routes: Routes = [
	{ path: "teams", component: TeamsComponent },
	{ path: "**", component: LoginComponent }
];

@NgModule({
	imports: [RouterModule.forRoot(routes)],
	exports: [RouterModule]
})
export class AppRoutingModule { }
