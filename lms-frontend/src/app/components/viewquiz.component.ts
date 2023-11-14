import { Component, inject } from '@angular/core';
import { QuizService } from '../services/quiz.service';
import { CreateQuizResponse, Quiz } from '../models';
import { FormGroup, FormBuilder, Validators, FormArray, FormControl } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { ClassService } from '../services/class.service';
import { AccountService } from '../services/account.service';

@Component({
  selector: 'app-viewquiz',
  templateUrl: './viewquiz.component.html',
  styleUrls: ['./viewquiz.component.css']
})
export class ViewquizComponent {

  updateQuizForm!: FormGroup
  quizUpdateResponse$!: Promise<CreateQuizResponse>
  successMessage!: string;
  currentQuestionType!: 'MCQ' | 'FreeResponse';

  quiz$!: Promise<Quiz>
  classes$!: Promise<string[]>
  quizSvc = inject(QuizService)
  accountSvc = inject(AccountService)
  accountId!:string
  quiz_id!: string
  selectedClassesArray!:FormArray
  classArray!: string[]
  router = inject(Router)
  fb = inject(FormBuilder)
  classSvc = inject(ClassService)

  ngOnInit(): void{
    this.quiz_id = this.quizSvc.quiz_id
    this.accountId = this.accountSvc.account_id
    console.info("the quiz_id in view quiz is ", this.quiz_id)
    this.updateQuizForm = this.createForm()

  }


  private createForm(): FormGroup {


    this.quiz$ = this.quizSvc.getQuiz(this.quiz_id)
    this.classes$=this.classSvc.getClasses(this.accountId)
    this.classes$.then(classes=> {
      this.classArray = classes;
      console.log('Classes data', classes)
      this.selectedClassesArray = this.updateQuizForm.get('selectedClasses') as FormArray;
      classes.forEach((classItem) => {
        this.selectedClassesArray.push(new FormControl(false)); 
      });
    })
    
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
      questions: this.fb.array([]),
      selectedClasses: this.fb.array([]),
    });


    this.quiz$.then((quizData) => {
    
      console.info('quizData in viewQuiz:', quizData)
    
    
      const quizFormData = {
        ...quizData,
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

  get classControls() {
    return (this.updateQuizForm.get('classes') as FormArray).controls;
  }


  // get questions() : FormArray {
  //   return this.quizForm.get("questions") as FormArray
  // }


  deleteQuiz(quizId: string){
    console.info("deleting quiz with id ", quizId)
  
    this.quizSvc.removeFromAllQuiz(quizId)
      .then(() => {
        console.info('Quiz removed successfully');
        this.router.navigate(['/dashboard'])
      })
  }

  
  saveQuiz(quizId: string){

    this.classes$.then(classNames => {
      this.classArray = classNames;
    });

    console.info("saving quiz with id ", quizId)
    const updatedQuizData:Quiz = this.updateQuizForm.value
    console.info('>> data: ', updatedQuizData)

    const selectedClasses = this.updateQuizForm.get('selectedClasses')?.value;
    if (selectedClasses && Array.isArray(selectedClasses)) {
      for (let i = 0; i < selectedClasses.length; i++) {
        if (selectedClasses[i] === true) {
          selectedClasses[i] = this.classArray[i];
        }
        else{
          selectedClasses[i] = null
        }
      }
    }

    for (let i = 0; i < selectedClasses.length; i++) {
      if (selectedClasses[i] === null) {
        selectedClasses.splice(i, 1);
        i--;
      }
    }

    updatedQuizData.classes = selectedClasses
    console.info('The updated classes are: ', selectedClasses)
    

    this.quizUpdateResponse$=firstValueFrom(this.quizSvc.createQuiz(updatedQuizData))
    this.quizUpdateResponse$.then((response) => {
      console.log('status:', response.status);

    this.updateQuizForm.reset
    this.successMessage = 'Saving changes...'
    setTimeout(() => {
      this.router.navigate(['/dashboard']);
    }, 2000); 

    })
  
  }


  deleteQuestion(index: number) {
    console.info("Deleting quiz question with index ", index);
    const questionsArray = this.updateQuizForm.get('questions') as FormArray;
    questionsArray.removeAt(index);
}


  addMCQ() {
    this.currentQuestionType = 'MCQ';
    const newMCQ = this.createNewMCQ();
    // this.questionControls.push(newMCQ);
    (this.updateQuizForm.get('questions') as FormArray).push(newMCQ);
  }

  addFreeResponse() {
    this.currentQuestionType = 'FreeResponse';
    const newFreeResponse = this.createNewFreeResponse();
    // this.questionControls.push(this.createNewFreeResponse());
    (this.updateQuizForm.get('questions') as FormArray).push(newFreeResponse);
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


  createNewFreeResponse(): FormGroup {
    return this.fb.group({
      questionType: 'FreeResponse',
      question: this.fb.control<string>('What is 1+1', [Validators.required]),
      answer: this.fb.control<string>('', [Validators.required]),
    })
  }


}
