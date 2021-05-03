import { Injectable } from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Router} from '@angular/router';
import {MatDialog} from '@angular/material/dialog';
import {Observable, of, throwError} from 'rxjs';
import {catchError} from 'rxjs/operators';
import {MessageComponent} from '../message/message.component';

@Injectable()
export class AuthInterceptorService implements HttpInterceptor {

  constructor(private router: Router, private dialog: MatDialog) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    return next.handle(req).pipe(catchError(x => this.handleAuthError(x)));
  }

  private handleAuthError(err: HttpErrorResponse): Observable<any> {
   if (err.status >= 400) {
      this.dialog.open(MessageComponent, {
        width: '200',
        data:  err.error.message
      });
    }

   return throwError(err);
  }
}
