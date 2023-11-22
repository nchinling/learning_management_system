import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http'
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './components/login.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import { NavbarComponent } from './components/navbar.component';
import { MainComponent } from './components/main.component';
import { DashboardComponent } from './components/dashboard.component';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import {MatFormFieldModule} from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import {MatTabsModule} from '@angular/material/tabs';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatCardModule} from '@angular/material/card';
import {MatDividerModule} from '@angular/material/divider';
import { AccountService } from './services/account.service';
import { RegisterComponent } from './components/register.component';
import { QuizComponent } from './components/quiz.component';
import { MatRadioModule } from '@angular/material/radio';
import {MatSelectModule} from '@angular/material/select';
import { QuizService } from './services/quiz.service';
import { ViewquizComponent } from './components/viewquiz.component';
import { ClassService } from './services/class.service';
import { StudentdashboardComponent } from './components/studentdashboard.component';
import { StudentquizComponent } from './components/studentquiz.component';
import { CreatenotesComponent } from './components/createnotes.component';
import { ContentService } from './services/content.service';
import { EditnotesComponent } from './components/editnotes.component';
import { StudentcontentComponent } from './components/studentcontent.component';


@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    NavbarComponent,
    MainComponent,
    DashboardComponent,
    RegisterComponent,
    QuizComponent,
    ViewquizComponent,
    StudentdashboardComponent,
    StudentquizComponent,
    CreatenotesComponent,
    EditnotesComponent,
    StudentcontentComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    ReactiveFormsModule, 
    FormsModule, 
    HttpClientModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatExpansionModule,
    MatProgressSpinnerModule,
    MatCardModule,
    MatDividerModule,
    MatIconModule,
    MatRadioModule,
    MatSelectModule,
    MatTabsModule,

   
  ],
  providers: [AccountService, QuizService, ContentService, ClassService],
  bootstrap: [AppComponent]
})
export class AppModule { }
