import { Component } from "@angular/core";
import { Team } from "../app.models";
import { RequireAuthenticationComponent } from "../require-authentication.component";

@Component({
    selector: "app-teams",
    templateUrl: "./teams.component.html",
    styleUrls: ["./teams.component.scss"]
})
export class TeamsComponent extends RequireAuthenticationComponent {
    allTeams: Team[] = [];
    userTeams: Team[] = [];

    onInit() {
        this.fetchAllTeams();
        this.fetchTeamsForUser();
    }

    async fetchAllTeams() {
        this.allTeams = await this.service.getAllTeams();
    }

    async fetchTeamsForUser() {
        this.userTeams = await this.service.getTeamsByUserName();
    }
}