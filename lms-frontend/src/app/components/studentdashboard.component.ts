import { Component, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CreateQuizResponse } from '../models';
import { AccountService } from '../services/account.service';
import { QuizService } from '../services/quiz.service';

@Component({
  selector: 'app-studentdashboard',
  templateUrl: './studentdashboard.component.html',
  styleUrls: ['./studentdashboard.component.css']
})
export class StudentdashboardComponent {

  router = inject(Router)
  quizSvc = inject(QuizService)
  accountSvc = inject(AccountService)
  studentClass!: string
  name!: string
  allStudentQuizCreated$!:Promise<CreateQuizResponse[]>

  ngOnInit(): void {

    this.name = this.accountSvc.name
    this.studentClass = this.accountSvc.studentClass
    console.log('Student class at student dashboard is ', this.studentClass)
    this.allStudentQuizCreated$ = this.quizSvc.getAllStudentQuiz(this.studentClass)
    
  }


  viewQuiz(quiz_id:string){
    console.info('Printed the quiz_id:'+ quiz_id)
    this.quizSvc.quiz_id = quiz_id 
    this.router.navigate(['viewquiz']);
  
  }

}
