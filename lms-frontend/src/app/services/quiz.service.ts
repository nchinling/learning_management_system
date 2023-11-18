import { HttpParams, HttpHeaders, HttpClient } from "@angular/common/http";
import { Observable, filter, tap, Subject, map, lastValueFrom, firstValueFrom } from "rxjs";
import { CreateQuizResponse, MarkedQuizResponse, Quiz } from "../models";
import { Injectable, inject } from "@angular/core";
import { Router } from "@angular/router";
import { AccountService } from "./account.service";


const URL_API_SERVER = 'http://localhost:8080/api'
// const URL_API_TRADE_SERVER = '/api'

@Injectable()
export class QuizService {

  accountSvc=inject(AccountService)
  account_id!: string
  quiz_id!: string

  http=inject(HttpClient)
  router = inject(Router)
  onCreateQuizRequest = new Subject<CreateQuizResponse>()

  createQuiz(quizData: Quiz ): Observable<CreateQuizResponse> {

    if (!quizData.quiz_id) {
      const quizId = Math.random().toString(36).substring(2, 10);
      quizData.quiz_id = quizId;
      console.log(`The quizId after generating is: ${quizData.quiz_id}`);
  }

  console.info('The classes in quizData are: ', quizData.classes)
  

    const form = new HttpParams()
      .set("accountId", quizData.account_id)
      .set("quizId", quizData.quiz_id)
      .set("title", quizData.title)
      .set("classes", JSON.stringify(quizData.classes)) 
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


  getAllQuizCreated(account_id: string): Promise<CreateQuizResponse[]> {
  
    const queryParams = new HttpParams()
      .set('account_id', account_id);

    console.info("I am inside getAllQuizCreated service", account_id)
  
    return lastValueFrom(this.http.get<CreateQuizResponse[]>(`${URL_API_SERVER}/getAllQuiz`, { params: queryParams })
    .pipe(
      filter((respArray) => respArray !== null), 
      map(respArray => respArray.map(resp => ({

        account_id: resp.account_id, 
        quiz_id: resp.quiz_id,
        title: resp.title,
        status: resp.status

      })))
    )
    )

  }


  getAllStudentQuiz(studentClass: string): Promise<CreateQuizResponse[]> {
  
    const queryParams = new HttpParams()
      .set('studentClass', studentClass);

    console.info("I am inside getAllStudentQuiz service:", studentClass)
  
    return lastValueFrom(this.http.get<CreateQuizResponse[]>(`${URL_API_SERVER}/getAllStudentQuiz`, { params: queryParams })
    .pipe(
      filter((respArray) => respArray !== null), 
      map(respArray => respArray.map(resp => ({

        account_id: resp.account_id, 
        quiz_id: resp.quiz_id,
        title: resp.title,
        status: resp.status

      })))
    )
    )

  }


  getQuiz(quiz_id:string): Promise<Quiz> {

    console.info('>>>>>>sending to Quiz server...')

    return lastValueFrom(
      this.http.get<Quiz>(`${URL_API_SERVER}/getQuiz/${quiz_id}`)
        .pipe(
          map(resp => ({ account_id: resp.account_id, title: resp.title, 
                      questions: resp.questions, quiz_id: resp.quiz_id, classes: resp.classes
                      }))
        )
    )
  } 


  removeFromAllQuiz(quiz_id:string) {
    console.info('>>>>>>sending to Quiz server...')

    console.info('>>>>>>sending to Quiz server2...')

    return firstValueFrom(
      this.http.get<string>(`${URL_API_SERVER}/removeQuiz/${quiz_id}`)
    );
  
  } 


  submitQuizForMarking(quizData: Quiz ): Observable<MarkedQuizResponse> {

    const form = new HttpParams()
      .set("accountId", quizData.account_id)
      .set("quizId", quizData.quiz_id)
      .set("title", quizData.title)
      .set("questions", JSON.stringify(quizData.questions)); 

    const headers = new HttpHeaders()
      .set("Content-Type", "application/x-www-form-urlencoded")

    return this.http.post<MarkedQuizResponse>(`${URL_API_SERVER}/markQuiz`, form.toString(), {headers}).pipe(
      filter((response) => response !== null), 
        map(response => ({ account_id: response.account_id, quiz_id: response.quiz_id, 
                    title: response.title, questions: response.questions, total_marks: response.total_marks, marks: response.marks
                    }))

    );
    
  }

  
}