import { Component, inject } from '@angular/core';
import { QuizService } from '../services/quiz.service';
import { CreateQuizResponse, Quiz } from '../models';
import { FormGroup, FormBuilder, Validators, FormArray } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';

@Component({
  selector: 'app-viewquiz',
  templateUrl: './viewquiz.component.html',
  styleUrls: ['./viewquiz.component.css']
})
export class ViewquizComponent {

  updateQuizForm!: FormGroup
  quizUpdateResponse$!: Promise<CreateQuizResponse>
  successMessage!: string;

  quiz$!: Promise<Quiz>
  quizSvc = inject(QuizService)
  quiz_id!: string
  router = inject(Router)
  fb = inject(FormBuilder)

  ngOnInit(): void{
    this.quiz_id = this.quizSvc.quiz_id
    console.info("the quiz_id in view quiz is ", this.quiz_id)
    // this.quiz$ = this.quizSvc.getQuiz(this.quiz_id)
    this.updateQuizForm = this.createForm()

  }


  private createForm(): FormGroup {
 
    this.quiz$ = this.quizSvc.getQuiz(this.quiz_id)
    
    const defaultQuestion = this.fb.group({
      question: [''],
      questionType: [''],
      option1: [''],
      option2: [''],
      option3: [''],
      option4: [''],
      answer: ['']
    });
    const formGroup = this.fb.group({
      title: [''],
      quiz_id: [''],
      questions: this.fb.array([])
      // questions: this.fb.array([defaultQuestion]) 
    });


    this.quiz$.then((quizData) => {
    
      console.info('quizData:', quizData)
    
      const quizFormData = {
        ...quizData
      };
      formGroup.patchValue(quizFormData);

        // Add questions to the form array
        if (quizData.questions && quizData.questions.length > 0) {
          const questionArray = formGroup.get('questions') as FormArray;
          questionArray.clear(); 
    
          quizData.questions.forEach((question) => {
            questionArray.push(
              this.fb.group({
                question: [question.question],
                questionType: [question.questionType],
                option1: [question.option1],
                option2: [question.option2],
                option3: [question.option3],
                option4: [question.option4],
                answer: [question.answer],
              })
            );
          });
        }
      });

    return formGroup;
  }


  get questionControls() {
    return (this.updateQuizForm.get('questions') as FormArray).controls;
  }


  deleteQuiz(quizId: string){
    console.info("deleting quiz with id ", quizId)
  
    this.quizSvc.removeFromAllQuiz(quizId)
      .then(() => {
        console.info('Quiz removed successfully');
        this.router.navigate(['/dashboard'])
      })
  }

  saveQuiz(quizId: string){
    console.info("saving quiz with id ", quizId)
    const updatedQuizData:Quiz = this.updateQuizForm.value
    console.info('>> data: ', updatedQuizData)

    this.quizUpdateResponse$=firstValueFrom(this.quizSvc.createQuiz(updatedQuizData))
    this.quizUpdateResponse$.then((response) => {
      console.log('status:', response.status);

    this.updateQuizForm.reset
    this.successMessage = 'Account has been successfully updated.'
    setTimeout(() => {
      this.router.navigate(['/dashboard']);
    }, 2000); 

    })
  
  }


  deleteQuestion(index: number){
    console.info("deleting quiz question with index ", index)
    
  }






}
