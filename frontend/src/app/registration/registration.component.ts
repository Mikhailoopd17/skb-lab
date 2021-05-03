import { Component, OnInit } from '@angular/core';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {UserData} from '../model/user-data';
import {RegistrationService} from '../service/registration.service';
import {catchError, first} from 'rxjs/operators';
import {MatDialog} from '@angular/material/dialog';
import {MessageComponent} from '../message/message.component';

@Component({
  selector: 'app-registration',
  templateUrl: './registration.component.html',
  styleUrls: ['./registration.component.css']
})
export class RegistrationComponent implements OnInit {
  form: FormGroup;
  constructor(private formBuilder: FormBuilder,
              private registrationService: RegistrationService,
              public dialog: MatDialog) { }

  ngOnInit(): void {
    this.form = this.formBuilder.group({
      login: ['', Validators.required],
      password: ['', Validators.required],
      email: ['', Validators.required],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required]
    });
  }

  /**
   *
   */
  onCancel(): void {
    document.location.href = '/registration';
  }


  onSubmit(): void {
    if (this.form.invalid) {
      return;
    }
    const controls = this.form.controls;
    const userData: UserData = {
      login: controls.login.value,
      password: controls.password.value,
      email: controls.email.value,
      firstName: controls.firstName.value,
      lastName: controls.lastName.value
    };
    this.registrationService.login(userData)
      .pipe(first())
      .subscribe(() => this.dialog.open(MessageComponent, {
        width: '300px',
        data: 'Заявка на регистрацию принята, проверьте Ваш почтовый ящик ' + userData.email
      }));
  }
}
