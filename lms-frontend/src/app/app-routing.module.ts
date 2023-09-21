import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './components/login.component';
import { MainComponent } from './components/main.component';

const routes: Routes = [
  { path: '', component: MainComponent, title: 'Home' },
  { path: 'login', component: LoginComponent, title: 'Log In' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
