import { HttpClient } from "@angular/common/http";
import { Injectable, inject } from "@angular/core";
import { firstValueFrom } from "rxjs";

const URL_API_SERVER = 'http://localhost:8080/api'

@Injectable()
export class ClassService {

    
http=inject(HttpClient)
classList: string[] = []

    async getClasses(accountId: string): Promise<string[]> {
        return firstValueFrom(
            this.http.get<any[]>(`${URL_API_SERVER}/getClasses?accountId=${accountId}`)
            ).then((response: any[]) => {
                this.classList = response.map((item) => item.class);
                console.info('the classes obtained from the server are: '+this.classList)
                return this.classList;
              });

        
    }

}