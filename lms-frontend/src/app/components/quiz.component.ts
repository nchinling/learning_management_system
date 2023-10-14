import { Component, inject } from '@angular/core';
import { CreateQuizResponse, Quiz, QuizQuestion } from '../models';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from '../services/account.service';
import { Observable, firstValueFrom } from 'rxjs';
import { QuizService } from '../services/quiz.service';

  @Component({
    selector: 'app-quiz',
    templateUrl: './quiz.component.html',
    styleUrls: ['./quiz.component.css']
  })
  export class QuizComponent {

    question!: QuizQuestion
    quizForm!: FormGroup
    optionForm!: FormGroup
    questionFormGroup!: FormGroup
    createQuiz$!: Promise<CreateQuizResponse>
    errorMessage!: string;
   
    fb = inject(FormBuilder)
    router = inject(Router)
    accountSvc = inject(AccountService)
    quizSvc = inject(QuizService)

    currentQuestionType!: 'MCQ' | 'FreeResponse';

    title = 'Quiz Creator';

    constructor() {
   
    this.quizForm = this.fb.group({
        title: '',
        questions: this.fb.array([]) ,
        
      });
    
    }
   
    get questions() : FormArray {
      return this.quizForm.get("questions") as FormArray
    }

    createNewMCQ(): FormGroup {
      return this.fb.group({
        questionType: 'MCQ',
        question: this.fb.control<string>('What is 1+1', [Validators.required]),
        option1: this.fb.control<string>('2', [Validators.required]),
        option2: this.fb.control<string>('3', [Validators.required]),
        option3: this.fb.control<string>('4', [Validators.required]),
        option4: this.fb.control<string>('5', [Validators.required]),
        answer: this.fb.control<string>('', [Validators.required]),
      })
    }

    // createNewMCQ(): FormGroup {
    //   return this.fb.group({
    //     questionType: 'MCQ',
    //     question: this.fb.control<string>('What is 1+1', [Validators.required]),
    //     options: this.fb.array([
    //       this.fb.control<string>('2', [Validators.required]),
    //       this.fb.control<string>('3', [Validators.required]),
    //       this.fb.control<string>('4', [Validators.required]),
    //       this.fb.control<string>('5', [Validators.required]),
    //     ]),
    //     answer: this.fb.control<string>('', [Validators.required]),
    //   });
    // }

    createNewFreeResponse(): FormGroup {
      return this.fb.group({
        questionType: 'FreeResponse',
        question: this.fb.control<string>('What is 1+1', [Validators.required]),
        answer: this.fb.control<string>('', [Validators.required]),
      })
    }

    createNewOption():FormGroup {
      return this.fb.group({
        option: this.fb.control<string>('2', [Validators.required]),
      })
    }


    addMCQ() {
      this.currentQuestionType = 'MCQ';
      this.questions.push(this.createNewMCQ());
    }

    addFreeResponse() {
      this.currentQuestionType = 'FreeResponse';
      this.questions.push(this.createNewFreeResponse());
    }

    removeQuestion(i:number) {
      this.questions.removeAt(i);
    }

    processQuiz() {
      console.log(this.quizForm.value);

      const quizData:Quiz = this.quizForm.value
      quizData.account_id = this.accountSvc.account_id

      console.info("quizData account id is", quizData.account_id)
    
    this.createQuiz$=firstValueFrom(this.quizSvc.createQuiz(quizData))
    this.createQuiz$.then((response) => {
      console.log('account_id:', response.account_id);
      console.log('quiz_id:', response.quiz_id);
      console.log('title:', response.title);
      console.log('status:' , response.status)
      const queryParams = {
      account_id: response.account_id,
      };

    this.router.navigate(['/dashboard'], { queryParams: queryParams })
    }).catch((error)=>{
    
      this.errorMessage = error.error;
      console.info('this.errorMessage is ' + this.errorMessage)
  
    });

    }

   
  }
   
   

  
