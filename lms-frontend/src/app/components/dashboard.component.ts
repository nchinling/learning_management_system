import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { QuizService } from '../services/quiz.service';
import { AccountService } from '../services/account.service';
import { CreateContentResponse, CreateQuizResponse } from '../models';
import { ContentService } from '../services/content.service';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {

  router = inject(Router)
  contentSvc = inject(ContentService)
  quizSvc = inject(QuizService)
  accountSvc = inject(AccountService)
  accountId!: string
  allQuizCreated$!:Promise<CreateQuizResponse[]>
  allContentCreated$!: Promise<CreateContentResponse[]>

  ngOnInit(): void {


    this.accountId = this.accountSvc.account_id
    console.log('Account id at dashboard is ', this.accountId)
    this.allQuizCreated$ = this.quizSvc.getAllQuizCreated(this.accountId)
    this.allContentCreated$ = this.contentSvc.getAllContentCreated(this.accountId)
    
  }


  viewQuiz(quiz_id:string){
    console.info('Printed the quiz_id:'+ quiz_id)
    this.quizSvc.quiz_id = quiz_id 
    this.router.navigate(['viewquiz']);
  
  }

  viewContent(content_id:string){
    console.info('Printed the content_id:'+ content_id)
    this.contentSvc.content_id = content_id 
    this.router.navigate(['editnotes']);
  
  }



}
