import { Component, OnInit, inject } from '@angular/core';
import { FormGroup, FormArray, FormBuilder, FormControl, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { Content, CreateContentResponse, Quiz } from '../models';
import { AccountService } from '../services/account.service';
import { ClassService } from '../services/class.service';
import { ContentService } from '../services/content.service';


@Component({
  selector: 'app-editnotes',
  templateUrl: './editnotes.component.html',
  styleUrls: ['./editnotes.component.css']
})
export class EditnotesComponent implements OnInit {

  
  updateContentForm!: FormGroup
  contentUpdateResponse$!: Promise<CreateContentResponse>
  successMessage!: string;

  content$!: Promise<Content>
  classes$!: Promise<string[]>
  contentSvc = inject(ContentService)
  accountSvc = inject(AccountService)
  accountId!:string
  content_id!: string
  marker!: boolean
  selectedClassesArray!:FormArray
  classArray!: string[]
  contentClassArray!: string[]
  router = inject(Router)
  fb = inject(FormBuilder)
  classSvc = inject(ClassService)

  ngOnInit(): void{
    this.content_id = this.contentSvc.content_id
    this.accountId = this.accountSvc.account_id
    console.info("the content_id in edit content is ", this.content_id)
    this.updateContentForm = this.createForm()

  }


  private createForm(): FormGroup {

    this.content$ = this.contentSvc.getContent(this.content_id)
    this.content$.then(contentData=> {
    this.contentClassArray = contentData.classes

      console.info('this.contentClassArray is: ', this.contentClassArray)
      this.classes$=this.classSvc.getClasses(this.accountId)
      this.classes$.then(classes=> {
        this.classArray = classes;
        console.log('Classes data', classes)
        this.selectedClassesArray = this.updateContentForm.get('selectedClasses') as FormArray;

      
        classes.forEach((classItem) => {
          const isChecked = this.contentClassArray.includes(classItem);
          this.selectedClassesArray.push(new FormControl(isChecked));
        });
        

    })
    
    })
    

    const formGroup = this.fb.group({
      title: ['', [Validators.required] ],
      // answer: this.fb.control<string>('', [Validators.required]),
      content_id: [''],
      contents: this.fb.array([]),
      selectedClasses: this.fb.array([]),
    });


    this.content$.then((contentData) => {
    
      console.info('contentData in editNotes:', contentData)
    
    
      const contentFormData = {
        ...contentData,
      };
      formGroup.patchValue(contentFormData);

        // Add questions to the form array
        if (contentData.contents && contentData.contents.length > 0) {
          const contentArray = formGroup.get('contents') as FormArray;
          contentArray.clear(); 
    
          contentData.contents.forEach((content) => {
            contentArray.push(
              this.fb.group({
                sectionTitle: [content.sectionTitle, [Validators.required]],
                notes: [content.notes, [Validators.required]],
         
              })
            );
          });


        }
      });

    return formGroup;
    
  }


  get contentControls() {
    return (this.updateContentForm.get('contents') as FormArray).controls;
  }

  get classControls() {
    return (this.updateContentForm.get('classes') as FormArray).controls;
  }


  deleteContent(contentId: string){
    console.info("deleting content with id ", contentId)
  
    this.contentSvc.removeFromAllContents(contentId)
      .then(() => {
        console.info('Content removed successfully');
        this.router.navigate(['/dashboard'])
      })
  }

  
  saveContent(contentId: string){

    this.classes$.then(classNames => {
      this.classArray = classNames;
    });

    console.info("saving content with id ", contentId)
    const updatedContentData:Content = this.updateContentForm.value
    console.info('>> data: ', updatedContentData)

    const selectedClasses = this.updateContentForm.get('selectedClasses')?.value;
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

    updatedContentData.classes = selectedClasses
    console.info('The updated classes are: ', selectedClasses)
    

    this.contentUpdateResponse$=firstValueFrom(this.contentSvc.createContent(updatedContentData))
    this.contentUpdateResponse$.then((response) => {
      console.log('status:', response.status);

    this.updateContentForm.reset
    this.successMessage = 'Saving changes...'
    setTimeout(() => {
      this.router.navigate(['/dashboard']);
    }, 2000); 

    })
  
  }


  deleteSection(index: number) {
    console.info("Deleting content section with index ", index);
    const contentsArray = this.updateContentForm.get('contents') as FormArray;
    contentsArray.removeAt(index);
}



  addSection() {
    const newSection = this.createNewSection();
    // this.questionControls.push(this.createNewFreeResponse());
    (this.updateContentForm.get('contents') as FormArray).push(newSection);
  }



  createNewSection(): FormGroup {
    return this.fb.group({
      sectionTitle: this.fb.control<string>('', [Validators.required]),
      notes: this.fb.control<string>('', [Validators.required]),
    })
  }


  invalidContentField(ctrlName:string): boolean{
    return !!(this.updateContentForm.get(ctrlName)?.invalid && this.updateContentForm.get(ctrlName)?.dirty)
  }
}
