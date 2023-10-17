import { Component, inject } from '@angular/core';
import { QuizService } from '../services/quiz.service';
import { Quiz } from '../models';

@Component({
  selector: 'app-viewquiz',
  templateUrl: './viewquiz.component.html',
  styleUrls: ['./viewquiz.component.css']
})
export class ViewquizComponent {

  quiz$!: Promise<Quiz>
  quizSvc = inject(QuizService)
  quiz_id!: string

  ngOnInit(): void{
    this.quiz_id = this.quizSvc.quiz_id
    console.info("the quiz_id in view quiz is ", this.quiz_id)
    this.quiz$ = this.quizSvc.getQuiz(this.quiz_id)
  }
}
