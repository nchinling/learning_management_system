import { Component, inject } from '@angular/core';
import { QuizQuestion } from '../models';
import { FormArray, FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from '../services/account.service';

  @Component({
    selector: 'app-quiz',
    templateUrl: './quiz.component.html',
    styleUrls: ['./quiz.component.css']
  })
  export class QuizComponent {

    question!: QuizQuestion
    // questions!: FormArray
    quizForm!: FormGroup
    optionForm!: FormGroup
    questionFormGroup!: FormGroup
   

    fb = inject(FormBuilder)
    router = inject(Router)
    accountSvc = inject(AccountService)

    currentQuestionType: 'MCQ' | 'FreeResponse' = 'MCQ';

    title = 'FormArray Example in Angular Reactive forms';

    constructor() {
   
    this.quizForm = this.fb.group({
        title: '',
        questions: this.fb.array([]) ,
        
      });
    
    }
   
    get questions() : FormArray {
      return this.quizForm.get("questions") as FormArray
    }

    get options() : FormArray {
      return this.quizForm.get("questions")?.get("options") as FormArray
    }


    createNewMCQ(): FormGroup {
      return this.fb.group({
        question: this.fb.control<string>('What is 1+1', [Validators.required]),
        option1: this.fb.control<string>('2', [Validators.required]),
        option2: this.fb.control<string>('3', [Validators.required]),
        option3: this.fb.control<string>('4', [Validators.required]),
        option4: this.fb.control<string>('5', [Validators.required]),
        answer: this.fb.control<string>('', [Validators.required]),
      })
    }

    createNewFreeResponse(): FormGroup {
      return this.fb.group({
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

    addOption() {
      this.options.push(this.createNewOption());
    }

    removeOption(i:number) {
      this.options.removeAt(i);
    }


    formMode: 'MCQ' | 'FreeResponse' = 'MCQ'; // Initial mode is MCQ

    toggleMode(mode: 'MCQ' | 'FreeResponse') {
      this.formMode = mode;
    }

    processQuiz() {
      console.log(this.quizForm.value);
    }

   
  }
   
   
  // export class country {
  //   id: string;
  //   name: string;
   
  //   constructor(id: string, name: string) {
  //     this.id = id;
  //     this.name = name;
  // }

    // ngOnInit(): void {

    //   this.createQuizForm(); 
     
    // }


    // createQuizForm(): void {
    //   // this.questions = this.quizForm.get('questions') as FormArray
    //   this.quizForm = this.fb.group({
    //     questions: this.fb.array([])
    //     // question: this.fb.control<string>('What is 1+1', [Validators.required]),
    //     // option1: this.fb.control<string>('2', [Validators.required]),
    //     // option2: this.fb.control<string>('3', [Validators.required]),
    //     // option3: this.fb.control<string>('4', [Validators.required]),
    //     // option4: this.fb.control<string>('5', [Validators.required]),
    //     // answer: this.fb.control<string>('6', [Validators.required]),

    //   })
    //   this.questions = this.quizForm.get('questions') as FormArray
    // }


    // addQuestion() {
    //   this.questions.push(this.quizForm);
  
    // }

    // processQuiz() {
    //   const quizData: QuizQuestion[] = [];
    
    //   for (let i = 0; i < this.questions.length; i++) {
    //     const questionFormGroup = this.questions.at(i) as FormGroup;
    
    //     const questionData: QuizQuestion = {
    //       quiz_question_id: '', 
    //       question: questionFormGroup.get('question')?.value || null,
    //       options: [
    //         questionFormGroup.get('option1')?.value || null,
    //         questionFormGroup.get('option2')?.value || null,
    //         questionFormGroup.get('option3')?.value || null,
    //         questionFormGroup.get('option4')?.value || null,
    //       ],
    //       answer: questionFormGroup.get('answer')?.value || null,
    //     };
    
    //     quizData.push(questionData);
    //   }
    
    //   console.log('Quiz Data:', quizData);
    // }
    


  
