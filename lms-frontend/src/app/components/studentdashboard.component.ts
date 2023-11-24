import { Component, OnInit, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CreateContentResponse, CreateQuizResponse } from '../models';
import { AccountService } from '../services/account.service';
import { QuizService } from '../services/quiz.service';
import { ContentService } from '../services/content.service';

@Component({
  selector: 'app-studentdashboard',
  templateUrl: './studentdashboard.component.html',
  styleUrls: ['./studentdashboard.component.css']
})
export class StudentdashboardComponent implements OnInit {

  router = inject(Router)
  quizSvc = inject(QuizService)
  contentSvc = inject(ContentService)
  accountSvc = inject(AccountService)
  studentClass!: string
  name!: string
  allStudentQuizCreated$!:Promise<CreateQuizResponse[]>
  allStudentContentCreated$!: Promise<CreateContentResponse[]>

  ngOnInit(): void {

    this.name = this.accountSvc.name
    this.studentClass = this.accountSvc.studentClass
    console.log('Student class at student dashboard is ', this.studentClass)
    this.allStudentQuizCreated$ = this.quizSvc.getAllStudentQuiz(this.studentClass)
    this.allStudentContentCreated$ = this.contentSvc.getAllStudentContent(this.studentClass)
  }


  viewQuiz(quiz_id:string){
    console.info('Printed the quiz_id:'+ quiz_id)
    this.quizSvc.quiz_id = quiz_id 
    this.router.navigate(['studentquiz']);
  
  }

  viewContent(content_id:string){
    console.info('Printed the content_id:'+ content_id)
    this.contentSvc.content_id = content_id 
    this.router.navigate(['studentcontent']);
  
  }

}
