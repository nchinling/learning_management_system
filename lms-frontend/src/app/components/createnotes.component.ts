import { Component, inject } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Content, CreateContentResponse } from '../models';
import { AccountService } from '../services/account.service';
import { ClassService } from '../services/class.service';
import { QuizService } from '../services/quiz.service';
import { firstValueFrom } from 'rxjs';
import { ContentService } from '../services/content.service';



@Component({
  selector: 'app-createnotes',
  templateUrl: './createnotes.component.html',
  styleUrls: ['./createnotes.component.css']
})
export class CreatenotesComponent  {


  contentForm!: FormGroup
  createContent$!:Promise<CreateContentResponse>
  // createQuiz$!: Promise<CreateQuizResponse>
  errorMessage!: string;
  renderedMath!: string;
  classes$!: Promise<string[]>
  accountId!: string

 
  fb = inject(FormBuilder)
  router = inject(Router)
  contentSvc = inject(ContentService)
  accountSvc = inject(AccountService)
  quizSvc = inject(QuizService)
  classSvc = inject(ClassService)


  title = 'Content Creator';

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
      contents: this.fb.array([]) ,
      selectedClasses: this.fb.array([]),
      
    });
  
  }


  get contents() : FormArray {
    return this.contentForm.get("contents") as FormArray
  }

  get selectedClasses() : FormArray {
    return this.contentForm.get("selectedClasses") as FormArray
  }



  createNewSection(): FormGroup {
    return this.fb.group({
      sectionTitle: this.fb.control<string>('', [Validators.required]),
      notes: this.fb.control<string>('', [Validators.required]),
    })
  }


  addSection() {
    this.contents.push(this.createNewSection());
  }

  removeSection(i:number) {
    this.contents.removeAt(i);
  }

  invalidContentField(ctrlName:string): boolean{
    return !!(this.contentForm.get(ctrlName)?.invalid && this.contentForm.get(ctrlName)?.dirty)
  }

  assignClass() {
    console.log('class assigned to: ')
  }

  processContent() {
    console.log('In process content:', this.contentForm.value);
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
 


    const contentData: Content = this.contentForm.value
    contentData.account_id = this.accountSvc.account_id
    contentData.classes = selectedClasses

    console.info("contentData account id is", contentData.account_id)
  
  this.createContent$=firstValueFrom(this.contentSvc.createContent(contentData))
  this.createContent$.then((response) => {
    console.log('account_id:', response.account_id);
    console.log('content_od:', response.content_id);
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
