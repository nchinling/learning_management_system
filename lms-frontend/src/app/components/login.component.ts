import { Component, inject } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Observable, firstValueFrom } from 'rxjs';
import { LoginResponse } from '../models';
import { AccountService } from '../services/account.service';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  
  login$!: Promise<LoginResponse>
  teacherLoginForm!: FormGroup
  studentLoginForm!: FormGroup
  errorMessage$!: Observable<string>
  errorMessage!: string;
  student!: boolean
  KEY = "username"
 

  isLoading = false;

  fb = inject(FormBuilder)
  router = inject(Router)
  accountSvc = inject(AccountService)

  ngOnInit(): void {
    // this.errorMessage$ = this.accountSvc.onErrorMessage;
    this.teacherLoginForm = this.fb.group({
      email: this.fb.control<string>('ncl@email.com', [Validators.required, Validators.pattern('[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}')]),
      password: this.fb.control<string>('#a888888', [ Validators.required, Validators.minLength(8), Validators.pattern('^(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).*$') ])
    })

    this.studentLoginForm = this.fb.group({
      email: this.fb.control<string>('student0@email.com', [Validators.required, Validators.pattern('[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}')]),
      password: this.fb.control<string>('student0@eduquest', [ Validators.required, Validators.minLength(8), Validators.pattern('^(?=.*[a-zA-Z])(?=.*[@#$%^&+=]).*$') ])
    })
  }


  invalidTeacherField(ctrlName:string): boolean{
    return !!(this.teacherLoginForm.get(ctrlName)?.invalid && this.teacherLoginForm.get(ctrlName)?.dirty)
  }

  invalidStudentField(ctrlName:string): boolean{
    return !!(this.studentLoginForm.get(ctrlName)?.invalid && this.studentLoginForm.get(ctrlName)?.dirty)
  }


  teacherLogin() {
    this.isLoading = true;
    const email = this.teacherLoginForm.get('email')?.value
    const password = this.teacherLoginForm.get('password')?.value
    this.student = false

    //for trade component
    this.accountSvc.email = email
    this.accountSvc.password = password
    

    console.info('email: ', email)
    console.info('password: ', password)

    setTimeout(() => {
      this.isLoading = false;
    }, 10000);


    //Using promise
    this.login$=firstValueFrom(this.accountSvc.login(email, password, this.student))
    this.login$.then((response) => {
      console.log('timestamp:', response.timestamp);
      console.log('username:', response.username);
      console.log('account_id:', response.account_id);
      const queryParams = {
        account_id: response.account_id,
        username: response.username,
       
      };

    this.router.navigate(['/dashboard'], { queryParams: queryParams })
    }).catch((error)=>{
    
      this.errorMessage = error.error;
      console.info('this.errorMessage is ' + this.errorMessage)
      this.isLoading = false;
    });

  }


  studentLogin() {
    this.isLoading = true;
    const email = this.studentLoginForm.get('email')?.value
    const password = this.studentLoginForm.get('password')?.value
    this.student = true

    //for trade component
    this.accountSvc.email = email
    this.accountSvc.password = password
    

    console.info('email: ', email)
    console.info('password: ', password)

    setTimeout(() => {
      this.isLoading = false;
    }, 10000);

    this.accountSvc.isStudent = true;


    //Using promise
    this.login$=firstValueFrom(this.accountSvc.login(email, password,this.student))
    this.login$.then((response) => {
      console.log('timestamp:', response.timestamp);
      console.log('username:', response.username);
      console.log('account_id:', response.account_id);
      const queryParams = {
        account_id: response.account_id,
        username: response.username,
       
      };

    this.accountSvc.isStudent = true

    this.router.navigate(['/studentdashboard'], { queryParams: queryParams })
    }).catch((error)=>{
    
      this.errorMessage = error.error;
      console.info('this.errorMessage is ' + this.errorMessage)
      this.isLoading = false;
    });

  }




}


