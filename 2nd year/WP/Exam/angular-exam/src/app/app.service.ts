import { HttpClient } from "@angular/common/http";
import { Injectable } from "@angular/core";
import { Team } from "./app.models";
import { sleep } from "./app.utils";
import { CookieService } from "./cookie.service";

@Injectable()
export class AppService {
    private BASE_URL = `http://localhost:80/api/php-exam/teams.php`;

    constructor(private http: HttpClient, private cookies: CookieService) {
    }

    async getAllTeams() {
        return await this.http.get<Team[]>(this.BASE_URL).toPromise();
    }

    async getTeamsByUserName() {
        const userName = this.cookies.getUserName();
        
        return await this.http.get<Team[]>(`${this.BASE_URL}/?userName=${userName}`).toPromise();
    }

    subscribe(onNotify: (data: any) => void) {
        this.checkForUpdates(onNotify);
    }

    private async checkForUpdates(onNotify: (data: any) => void) {
        while (true) {
            const data = await this.fetchUpdates();
            
            if (data) {
                onNotify(data);
            }

            await sleep(3000);
        }
    }

    private async fetchUpdates() {
        const username = this.cookies.getUserName();
        if (!username) return null;

        // Do fetch

        return null;
        // return await this.http.get<any>(`${this.BASE_URL}updates/${username}`);
    }
}