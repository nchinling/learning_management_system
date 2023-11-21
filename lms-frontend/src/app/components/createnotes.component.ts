import { Component, inject } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { QuizQuestion, CreateQuizResponse, Quiz } from '../models';
import { AccountService } from '../services/account.service';
import { ClassService } from '../services/class.service';
import { QuizService } from '../services/quiz.service';

@Component({
  selector: 'app-createnotes',
  templateUrl: './createnotes.component.html',
  styleUrls: ['./createnotes.component.css']
})
export class CreatenotesComponent {

  
  question!: QuizQuestion
  contentForm!: FormGroup
  optionForm!: FormGroup
  questionFormGroup!: FormGroup
  createQuiz$!: Promise<CreateQuizResponse>
  errorMessage!: string;
  renderedMath!: string;
  classes$!: Promise<string[]>
  accountId!: string
 
  fb = inject(FormBuilder)
  router = inject(Router)
  accountSvc = inject(AccountService)
  quizSvc = inject(QuizService)
  classSvc = inject(ClassService)
  

  currentQuestionType!: 'MCQ' | 'FreeResponse';

  title = 'Quiz Creator';

  constructor() {

  this.accountId = this.accountSvc.account_id
  this.classes$=this.classSvc.getClasses(this.accountId)
  this.classes$.then(classes=> {
    console.log('Classes data', classes)
    const selectedClassesArray = this.contentForm.get('selectedClasses') as FormArray;
    classes.forEach(() => {
      selectedClassesArray.push(new FormControl(false)); 
    });
  })


  this.contentForm = this.fb.group({
      title: '',
      sections: this.fb.array([]) ,
      selectedClasses: this.fb.array([]),
      
    });
  
  }
  

  get sections() : FormArray {
    return this.contentForm.get("sections") as FormArray
  }

  get selectedClasses() : FormArray {
    return this.contentForm.get("selectedClasses") as FormArray
  }



  createNewSection(): FormGroup {
    return this.fb.group({
      sectionTitle: this.fb.control<string>('', [Validators.required]),
      notes: this.fb.control<string>('What is 1+1', [Validators.required]),
    })
  }


  addSection() {
    this.sections.push(this.createNewSection());
  }

  removeSection(i:number) {
    this.sections.removeAt(i);
  }

  invalidContentField(ctrlName:string): boolean{
    return !!(this.contentForm.get(ctrlName)?.invalid && this.contentForm.get(ctrlName)?.dirty)
  }

  assignClass() {
    console.log('class assigned to: ')
  }

  processContent() {
    console.log('In process quiz:', this.contentForm.value);
    const selectedClasses = this.contentForm.get('selectedClasses')?.value;
    console.log('Selected Classes:', selectedClasses);

    this.classes$.then(classNames => {
      const selectedClasses = this.contentForm.get('selectedClasses')?.value;
      console.log('Selected Classes:', selectedClasses);

      if (selectedClasses && Array.isArray(selectedClasses)) {
        for (let i = 0; i < selectedClasses.length; i++) {
          if (selectedClasses[i] === true) {
            selectedClasses[i] = classNames[i];
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
      
    console.log('Modified Selected Classes:', selectedClasses);
 

    const quizData:Quiz = this.contentForm.value
    quizData.account_id = this.accountSvc.account_id
    quizData.classes = selectedClasses

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


    
});
          


  }
}
