import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login.component';
import { MainComponent } from './components/main.component';
import { DashboardComponent } from './components/dashboard.component';
import { RegisterComponent } from './components/register.component';
import { loginGuard } from './util';
import { QuizComponent } from './components/quiz.component';
import { ViewquizComponent } from './components/viewquiz.component';
import { StudentdashboardComponent } from './components/studentdashboard.component';
import { StudentquizComponent } from './components/studentquiz.component';
import { CreatenotesComponent } from './components/createnotes.component';
import { EditnotesComponent } from './components/editnotes.component';
import { StudentcontentComponent } from './components/studentcontent.component';

const routes: Routes = [
  { path: '', component: MainComponent, title: 'Main' },
  { path: 'login', component: LoginComponent, title: 'Log In' },
  { path: 'register', component: RegisterComponent, title: 'Register' },
  { path: 'dashboard', component: DashboardComponent, title: 'Dashboard', canActivate: [loginGuard] },
  { path: 'studentdashboard', component: StudentdashboardComponent, title: 'Dashboard', canActivate: [loginGuard] },
  { path: 'quiz', component: QuizComponent, title: 'Create Quiz', canActivate: [loginGuard] },
  { path: 'create_content', component: CreatenotesComponent, title: 'Create Content', canActivate: [loginGuard] },
  { path: 'viewquiz', component: ViewquizComponent, title: 'Quiz', canActivate: [loginGuard] },
  { path: 'editnotes', component: EditnotesComponent, title: 'Notes', canActivate: [loginGuard] },
  { path: 'studentquiz', component: StudentquizComponent, title: 'Student Quiz', canActivate: [loginGuard] },
  { path: 'studentcontent', component: StudentcontentComponent, title: 'Student Content', canActivate: [loginGuard] },

];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
