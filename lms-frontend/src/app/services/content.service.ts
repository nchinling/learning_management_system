import { HttpParams, HttpHeaders, HttpClient } from "@angular/common/http";
import { Observable, filter, tap, Subject, map, lastValueFrom, firstValueFrom } from "rxjs";
import { Content, CreateContentResponse } from "../models";
import { Injectable, inject } from "@angular/core";
import { Router } from "@angular/router";
import { AccountService } from "./account.service";


const URL_API_SERVER = 'http://localhost:8080/api'
// const URL_API_TRADE_SERVER = '/api'

@Injectable()
export class ContentService {

  accountSvc=inject(AccountService)
  account_id!: string
  content_id!: string

  http=inject(HttpClient)
  router = inject(Router)
  onCreateContentRequest = new Subject<CreateContentResponse>()

  createContent(contentData: Content ): Observable<CreateContentResponse> {

    if (!contentData.content_id) {
      const contentId = Math.random().toString(36).substring(2, 10);
      contentData.content_id = contentId;
      console.log(`The randomly generated contentId is: ${contentData.content_id}`);
  }

  console.info('The classes in contentData are: ', contentData.classes)
  console.info('The contents in contentData service are: ', contentData.contents)

  
    const form = new HttpParams()
      .set("accountId", contentData.account_id)
      .set("contentId", contentData.content_id)
      .set("title", contentData.title)
      .set("classes", JSON.stringify(contentData.classes)) 
      .set("content", JSON.stringify(contentData.contents));

    const formData = new FormData();
    formData.append('accountId', contentData.account_id);
    formData.append('contentId', contentData.content_id);
    formData.append('title', contentData.title);
    formData.append('classes', JSON.stringify(contentData.classes));

    // contentData.contents.forEach((contentNote, index) => {
    //   formData.append(`contents[${index}].sectionTitle`, contentNote.sectionTitle);
    //   formData.append(`contents[${index}].notes`, contentNote.notes);
    //   if (contentNote.image) {
    //     formData.append(`contents[${index}].image`, contentNote.image);
    //   }
    // });


        const headers = new HttpHeaders()
          .set("Content-Type", "application/x-www-form-urlencoded")

    // const headers = new HttpHeaders();
    // headers.append('Content-Type', 'multipart/form-data');

    return this.http.post<CreateContentResponse>(`${URL_API_SERVER}/saveContent`, form.toString(), {headers}).pipe(
      // return this.http.post<CreateContentResponse>(`${URL_API_SERVER}/saveContent`, formData, { headers }).pipe(
      filter((response) => response !== null), 
        tap(response => this.onCreateContentRequest.next(response)),
        map(response => ({ account_id: response.account_id, content_id: response.content_id, 
                    title: response.title, status:response.status, date_created: response.date_created,
                    date_edited: response.date_edited
                    
                    }))

    );
    
  }


  getAllContentCreated(account_id: string): Promise<CreateContentResponse[]> {
  
    const queryParams = new HttpParams()
      .set('account_id', account_id);

    console.info("I am inside getAllContentCreated service with account id: ", account_id)
  
    return lastValueFrom(this.http.get<CreateContentResponse[]>(`${URL_API_SERVER}/getAllContent`, { params: queryParams })
    .pipe(
      filter((respArray) => respArray !== null), 
      map(respArray => respArray.map(resp => ({

        account_id: resp.account_id, 
        content_id: resp.content_id,
        title: resp.title,
        status: resp.status,
        date_created: resp.date_created,
        date_edited: resp.date_edited

      })))
    )
    )

  }


  getAllStudentContent(studentClass: string): Promise<CreateContentResponse[]> {
  
    this.account_id = this.accountSvc.account_id
    const queryParams = new HttpParams()
      .set('studentClass', studentClass)
      .set('accountId', this.account_id)

    console.info("I am inside getAllStudentContent service:", studentClass)
  
    return lastValueFrom(this.http.get<CreateContentResponse[]>(`${URL_API_SERVER}/getAllStudentContent`, { params: queryParams })
    .pipe(
      filter((respArray) => respArray !== null), 
      map(respArray => respArray.map(resp => ({

        account_id: resp.account_id, 
        content_id: resp.content_id,
        title: resp.title,
        status: resp.status,
        date_created: resp.date_created,
        date_edited: resp.date_edited
        
      })))
    )
    )

  }


  getContent(content_id:string): Promise<Content> {

    console.info('>>>>>>sending to Content server...')

    return lastValueFrom(
      this.http.get<Content>(`${URL_API_SERVER}/getContent/${content_id}`)
        .pipe(
          map(resp => ({ account_id: resp.account_id, title: resp.title, 
                      contents: resp.contents, content_id: resp.content_id, classes: resp.classes, date_created: resp.date_created,
                      date_edited: resp.date_edited
                      }))
        )
    )
  } 


  removeFromAllContents(content_id:string) {
    console.info('>>>>>>sending to Content server...')


    return firstValueFrom(
      this.http.get<string>(`${URL_API_SERVER}/removeContent/${content_id}`)
    );
  
  } 


  
}