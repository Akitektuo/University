import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule, RoutingComponents } from './app-routing.module';
import { AppComponent } from './app.component';
import { CookieService as CookieManager } from "ngx-cookie-service";
import { FormsModule } from '@angular/forms';
import { CookieService } from './cookie.service';
import { RecipesService } from './recipes.service';

@NgModule({
  declarations: [
    AppComponent,
    RoutingComponents
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
    RecipesService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
