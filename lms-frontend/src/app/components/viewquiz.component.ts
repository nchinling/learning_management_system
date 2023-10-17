import { Component, inject } from '@angular/core';
import { QuizService } from '../services/quiz.service';
import { Quiz } from '../models';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-viewquiz',
  templateUrl: './viewquiz.component.html',
  styleUrls: ['./viewquiz.component.css']
})
export class ViewquizComponent {

  quiz$!: Promise<Quiz>
  quizSvc = inject(QuizService)
  quiz_id!: string
  router = inject(Router)

  ngOnInit(): void{
    this.quiz_id = this.quizSvc.quiz_id
    console.info("the quiz_id in view quiz is ", this.quiz_id)
    this.quiz$ = this.quizSvc.getQuiz(this.quiz_id)
  }


  deleteQuiz(quizId: string){
    console.info("deleting quiz with id ", quizId)
  
    this.quizSvc.removeFromAllQuiz(quizId)
      .then(() => {
        console.info('Quiz removed successfully');
        this.router.navigate(['/dashboard'])
      })
  }


  deleteQuestion(index: number){
    console.info("deleting quiz question with index ", index)
    
  }






}
