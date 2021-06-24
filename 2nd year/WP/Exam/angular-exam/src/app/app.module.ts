import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule, RoutingComponents } from './app-routing.module';
import { AppComponent } from './app.component';
import { CookieService as CookieManager } from "ngx-cookie-service";
import { FormsModule } from '@angular/forms';
import { CookieService } from './cookie.service';
import { AppService } from './app.service';
import { NotificationComponent } from './notification/notification.component';

@NgModule({
	declarations: [
		AppComponent,
		RoutingComponents,
		NotificationComponent
	],
	imports: [
		BrowserModule,
		AppRoutingModule,
		HttpClientModule,
		FormsModule
	],
	providers: [
		CookieManager,
		CookieService,
		AppService
	],
	bootstrap: [AppComponent]
})
export class AppModule { }
