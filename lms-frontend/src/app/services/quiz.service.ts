import { HttpParams, HttpHeaders, HttpErrorResponse, HttpClient } from "@angular/common/http";
import { Observable, catchError, throwError, filter, tap, Subject, map } from "rxjs";
import { CreateQuizResponse, LoginResponse, Quiz, RegisterResponse, UserData } from "../models";
import { Injectable, inject } from "@angular/core";
import { Router } from "@angular/router";
import { AccountService } from "./account.service";


const URL_API_SERVER = 'http://localhost:8080/api'
// const URL_API_TRADE_SERVER = '/api'

@Injectable()
export class QuizService {

  accountSvc=inject(AccountService)
  account_id!: string

  http=inject(HttpClient)
  router = inject(Router)
  onCreateQuizRequest = new Subject<CreateQuizResponse>()

  createQuiz(quizData: Quiz ): Observable<CreateQuizResponse> {

    const form = new HttpParams()
      .set("accountId", quizData.account_id)
      .set("title", quizData.title)
      .set("questions", JSON.stringify(quizData.questions)); 

    const headers = new HttpHeaders()
      .set("Content-Type", "application/x-www-form-urlencoded")

    return this.http.post<CreateQuizResponse>(`${URL_API_SERVER}/saveQuiz`, form.toString(), {headers}).pipe(
      filter((response) => response !== null), 
        tap(response => this.onCreateQuizRequest.next(response)),
        map(response => ({ account_id: response.account_id, quiz_id: response.quiz_id, 
                    title: response.title, status:response.status, 
                    }))

    );
    
  }


  
}