import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { BehaviorSubject, Observable, Subscription, of } from 'rxjs';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css']
})
export class NavbarComponent implements OnInit{
  isCollapsed = true;
    
  // isTeacherLoggedIn$!: Observable<boolean>
  // isStudentLoggedIn$!: Observable<boolean>
  isLoggedIn$!: Observable<boolean>
  isStudent!:boolean


  //check if can be renamed
  private isLoggedInSubscription: Subscription | undefined;
  KEY = "username"
  queryParams: any

  router = inject(Router)
  accountSvc = inject(AccountService)

  ngOnInit(): void {
    this.isLoggedInSubscription = this.accountSvc.isLoggedInChanged.subscribe(isLoggedIn => {
      
      this.isStudent = this.accountSvc.isStudent
      this.isLoggedIn$ = of(isLoggedIn)

      console.info('User is logged in: ' + isLoggedIn);
    });

  }

  logout(): void {
    // Clear the stored credentials 
    localStorage.removeItem(this.KEY);
    this.isLoggedIn$ = of(false);
    this.isStudent = false;
    this.accountSvc.isStudent = false;
    this.accountSvc.username=''
    this.accountSvc.password=''
    this.accountSvc.key=''
    this.router.navigate(['/'])
  }

  ngAfterViewInit(): void{
    this.isLoggedIn$ = this.accountSvc.isLoggedInChanged
  }

  ngOnChanges(): void{
    this.isLoggedIn$ = this.accountSvc.isLoggedInChanged

  }

  ngOnDestroy(): void {
    if (this.isLoggedInSubscription) {
      this.isLoggedInSubscription.unsubscribe();
    }
  }
  
}