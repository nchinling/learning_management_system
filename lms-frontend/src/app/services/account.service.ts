import { HttpParams, HttpHeaders, HttpErrorResponse, HttpClient } from "@angular/common/http";
import { Observable, catchError, throwError, filter, tap, Subject, firstValueFrom } from "rxjs";
import { LoginResponse, RegisterResponse, UserData } from "../models";
import { Injectable, inject } from "@angular/core";
import { Router } from "@angular/router";


const URL_API_SERVER = 'http://localhost:8080/api'
// const URL_API_TRADE_SERVER = '/api'

@Injectable()
export class AccountService {

  onLoginRequest = new Subject<LoginResponse>()
  onRegisterRequest = new Subject<RegisterResponse>()
  onErrorMessage = new Subject<string>()
  isLoggedInChanged = new Subject<boolean>()
  classes: string[] = []

  http=inject(HttpClient)
  router = inject(Router)

  username = "";
  email = "";
  password = "";
  queryParams: any;
  account_id = ""
  KEY = "username"
  key!: string

  hasLogin(): boolean {
    if(this.username&&this.password)
      localStorage.setItem(this.KEY, this.username)
      const isLoggedIn = !!(this.username && this.password);
      this.isLoggedInChanged.next(isLoggedIn);
    return isLoggedIn;
 
  }

  
  logout(): void {
    localStorage.removeItem(this.KEY);
  }

  isAuthenticated(): boolean {
    return localStorage.getItem(this.KEY) !== null;
  }


  registerAccount(data: UserData ): Observable<RegisterResponse> {

    const form = new HttpParams()
      .set("name", data.name)
      .set("username", data.username)
      .set("email", data.email)
      .set("password", data.password)


    const headers = new HttpHeaders()
      .set("Content-Type", "application/x-www-form-urlencoded")

    return this.http.post<RegisterResponse>(`${URL_API_SERVER}/register`, form.toString(), {headers}).pipe(
      filter((response) => response !== null), 
      //the fired onRequest.next is received in dashboard component's ngOnit 
      tap(response => this.onRegisterRequest.next(response))
    );
    
  }

login(email: string, password: string, student: boolean): Observable<LoginResponse> {


    const form = new HttpParams()
    .set("email", email)
    .set("password", password)
    .set("student", student)

    const headers = new HttpHeaders()
      .set("Content-Type", "application/x-www-form-urlencoded")

    return this.http.post<LoginResponse>(`${URL_API_SERVER}/login`, form.toString(), {headers}).pipe(
      catchError(error => {
        let errorMessage = 'An error occurred during login: ' + error.message;
        console.error(errorMessage);
        
        if (error instanceof HttpErrorResponse && error.status === 500) {
          const serverError = error.error.error; 
          errorMessage = serverError;
        }
        
        this.onErrorMessage.next(errorMessage);
        return throwError(() => ({ error: errorMessage }));
      }),
      filter((response) => response !== null), 
      //the fired onLoginRequest.next is received in dashboard component's ngOnit 
      tap(response => {
        console.info('Login response:', response);
        this.username = response.username;
        this.account_id = response.account_id;
        // Handle successful login response here
        this.onLoginRequest.next(response);
    
      })
      

    );
  }

  
}