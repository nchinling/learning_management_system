import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { QuizService } from '../services/quiz.service';
import { AccountService } from '../services/account.service';
import { CreateQuizResponse } from '../models';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  router = inject(Router)
  quizSvc = inject(QuizService)
  accountSvc = inject(AccountService)
  accountId!: string
  allQuizCreated$!:Promise<CreateQuizResponse[]>

  ngOnInit(): void {
    // subscription is not necessary
    // this.quizSvc.onCreateQuizRequest.subscribe((createQuizResponse) => {

    //   console.log('Received createQuizResponse:', createQuizResponse);
    //   this.accountId = this.accountSvc.account_id
    //   console.log('Account id at dashboard is ', this.accountId)
    //   this.allQuizCreated$ = this.quizSvc.getAllQuizCreated(this.accountId)

    // });

    this.accountId = this.accountSvc.account_id
    console.log('Account id at dashboard is ', this.accountId)
    this.allQuizCreated$ = this.quizSvc.getAllQuizCreated(this.accountId)
    
  }


  viewQuiz(quiz_id:string){
    console.info('Printed the quiz_id:'+ quiz_id)
    this.quizSvc.quiz_id = quiz_id 
    this.router.navigate(['viewquiz']);
  
  }



}
