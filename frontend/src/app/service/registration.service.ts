import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UserData} from '../model/user-data';

@Injectable({
  providedIn: 'root'
})
export class RegistrationService {

  constructor(private httpClient: HttpClient) { }

  public login(userData: UserData): Observable<object>{
    return this.httpClient.post('/api/registration', userData);
  }
}
