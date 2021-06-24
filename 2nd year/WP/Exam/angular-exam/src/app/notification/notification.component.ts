import { Component, OnInit } from "@angular/core";
import { AppService } from "../app.service";
import { sleep } from "../app.utils";

@Component({
    selector: "app-notification",
    templateUrl: "./notification.component.html",
    styleUrls: ["./notification.component.scss"]
})
export class NotificationComponent implements OnInit {
    data: any = null;

    constructor(private service: AppService) { }

    ngOnInit(): void {
        this.service.subscribe(this.handleNotification);
    }

    async handleNotification(data: any) {
        this.data = data;

        await sleep(2500);

        this.data = null;
    }
}