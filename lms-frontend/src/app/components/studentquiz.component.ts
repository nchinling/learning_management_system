import { Component, OnInit, inject } from '@angular/core';
import { FormArray, FormBuilder, FormGroup } from '@angular/forms';
import { Router } from '@angular/router';
import { MarkedQuizResponse, Quiz } from '../models';
import { QuizService } from '../services/quiz.service';
import { firstValueFrom } from 'rxjs';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-studentquiz',
  templateUrl: './studentquiz.component.html',
  styleUrls: ['./studentquiz.component.css']
})
export class StudentquizComponent implements OnInit {

  quiz$!: Promise<Quiz>
  quizSvc = inject(QuizService)
  quiz_id!: string
  marks!: string
  quizForm!: FormGroup;
  quizNoOfQuestions!: number
  router = inject(Router)
  fb = inject(FormBuilder)
  accountSvc = inject(AccountService)

  markedQuizResponse$!: Promise<MarkedQuizResponse>

  ngOnInit(): void{
    this.quiz_id = this.quizSvc.quiz_id
    console.info("the quiz_id in student viewquiz is ", this.quiz_id)
    this.quiz$ = this.quizSvc.getQuiz(this.quiz_id)
    console.info('quiz data is ', this.quiz$)
    this.quizForm = this.createForm()
   

  }

  
  private createForm(): FormGroup {
 
    this.quiz$ = this.quizSvc.getQuiz(this.quiz_id)
    
    // const defaultQuestion = this.fb.group({
    //   question: [''],
    //   questionType: [''],
    //   option1: [''],
    //   option2: [''],
    //   option3: [''],
    //   option4: [''],
    //   answer: ['']
    // });
    const formGroup = this.fb.group({
      title: [''],
      quiz_id: [''],
      questions: this.fb.array([])
  
    });


    this.quiz$.then((quizData) => {
    
      console.info('quizData:', quizData)
      this.quizNoOfQuestions = quizData.questions.length
    
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
                answer: '',
              })
            );
          });
        }
      });

    return formGroup;
  }

  get questionControls() {
    return (this.quizForm.get('questions') as FormArray).controls;
  }



  submitQuiz(quizId: string){
    
    console.info('submitted for marking: ', quizId)
    const submittedQuizData:Quiz = this.quizForm.value
    submittedQuizData.account_id = this.accountSvc.account_id
    console.info('>> submitedQuizData: ', submittedQuizData)

    this.markedQuizResponse$=firstValueFrom(this.quizSvc.submitQuizForMarking(submittedQuizData))
    this.markedQuizResponse$.then((response) => {
      console.log('returned response', response)
      console.log('marks:', response.marks);
      this.marks = 'You got ' + response.marks + ' out of ' + response.total_marks + ' marks!'

   
    // setTimeout(() => {
    //   this.router.navigate(['/dashboard']);
    // }, 2000); 

    })
  }





}
