import { Component, inject } from '@angular/core';
import { QuizQuestion } from '../models';
import { FormArray, FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AccountService } from '../services/account.service';

  @Component({
    selector: 'app-quiz',
    templateUrl: './quiz.component.html',
    styleUrls: ['./quiz.component.css']
  })
  export class QuizComponent {
    question!: QuizQuestion
    questions: QuizQuestion[] = []
    quizForm!: FormGroup
   
    fb = inject(FormBuilder)
    router = inject(Router)
    accountSvc = inject(AccountService)

    currentQuestionNumber: number = 1;


    ngOnInit(): void {

      this.createQuizForm(); 
    }

    createQuizForm(): void {
      this.quizForm = this.fb.group({
        question: this.fb.control<string>('What is 1+1', [Validators.required]),
        option1: this.fb.control<string>('', [Validators.required]),
        option2: this.fb.control<string>('', [Validators.required]),
        option3: this.fb.control<string>('', [Validators.required]),
        option4: this.fb.control<string>('', [Validators.required]),
        answer: this.fb.control<string>('', [Validators.required]),
      });
    }

 
    addQuestion() {
      const questionData: QuizQuestion = {
        quiz_question_id: '', 
        question: this.quizForm.get('question')?.value || null,
        options: [
          this.quizForm.get('option1')?.value || null,
          this.quizForm.get('option2')?.value || null,
          this.quizForm.get('option3')?.value || null,
          this.quizForm.get('option4')?.value || null,
        ],
        answer: this.quizForm.get('answer')?.value || null,
      };
  
      // Push the question to the questions array
      this.questions.push(questionData);
  
      this.currentQuestionNumber++;
      this.createQuizForm();
  
     
    }
    


    processQuiz() {

      // const quizQuestions: QuizQuestion[] = this.quizForm.value;

      // console.log('All quiz questions:', quizQuestions);

      const questionData: QuizQuestion = {
        quiz_question_id: '', 
        question: this.quizForm.get('question')?.value || null,
        options: [
          this.quizForm.get('option1')?.value || null,
          this.quizForm.get('option2')?.value || null,
          this.quizForm.get('option3')?.value || null,
          this.quizForm.get('option4')?.value || null,
        ],
        answer: this.quizForm.get('answer')?.value || null,
      };

      console.log(this.quizForm.value);
      console.log('the question in QuizQuestion is', questionData.question); 
      console.log('the answer in QuizQuestion is', questionData.answer); 
      console.log('the option in QuizQuestion is', questionData.options); 
      console.log('QuizQuestion data is', questionData); 
    
    }


  }
