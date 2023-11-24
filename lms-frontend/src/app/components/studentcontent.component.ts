import { Component, OnInit, inject } from '@angular/core';
import { FormGroup, FormBuilder, FormArray, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { firstValueFrom } from 'rxjs';
import { Content, Quiz } from '../models';
import { AccountService } from '../services/account.service';
import { ContentService } from '../services/content.service';

@Component({
  selector: 'app-studentcontent',
  templateUrl: './studentcontent.component.html',

  styleUrls: ['./studentcontent.component.css']
})
export class StudentcontentComponent implements OnInit {

  
  content$!: Promise<Content>
  contentSvc = inject(ContentService)
  content_id!: string
  
  contentForm!: FormGroup;
  contentNoOfSections!: number
  router = inject(Router)
  fb = inject(FormBuilder)
  accountSvc = inject(AccountService)

  ngOnInit(): void{
    this.content_id = this.contentSvc.content_id
    console.info("the content_id in student content is ", this.content_id)

    this.content$ = this.contentSvc.getContent(this.content_id)

    this.content$.then((content)=>{
      console.info('Content from the Promise: ', content)
    })
  
   
  }


}
