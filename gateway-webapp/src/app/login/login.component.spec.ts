/*
 * Copyright (C) 2019  Shamil Absalikov
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

import {async, ComponentFixture, TestBed} from '@angular/core/testing';

import {LoginComponent} from './login.component';
import {ReactiveFormsModule} from "@angular/forms";
import {MatFormFieldModule, MatIconModule, MatInputModule} from "@angular/material";
import {HttpClientModule} from "@angular/common/http";
import {NoopAnimationsModule} from "@angular/platform-browser/animations";
import {LoginService} from "./login.service";
import {of} from "rxjs";

describe('LoginComponent', () => {
  const username = 'john.doe@dark.net';
  const password = '123456';

  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let nativeElement: HTMLElement;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      imports: [
        MatFormFieldModule,
        MatIconModule,
        MatInputModule,
        ReactiveFormsModule,
        HttpClientModule,
        NoopAnimationsModule
      ],
      declarations: [LoginComponent],
      providers: [LoginService]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    nativeElement = fixture.nativeElement;
    fixture.detectChanges();
  });

  it('should create', () => expect(component).toBeTruthy());

  it('show/hide button should toggle password input type', () => {
    let passwordInput = getInput('#password');
    let toggleButton = getButton('#show-hide-password');

    expect(passwordInput.type).toBe('password');

    toggleButton.click();
    fixture.detectChanges();

    expect(passwordInput.type).toBe('text');

    toggleButton.click();
    fixture.detectChanges();

    expect(passwordInput.type).toBe('password');
  });

  it('submit button should submit form', () => {
    fixture.detectChanges();
    setInputValue('#username', username);
    setInputValue('#password', password);
    fixture.detectChanges();

    const service = fixture.debugElement.injector.get(LoginService);
    const serviceSpy = spyOn(service, 'login').and.returnValue(of({username, password}));

    let submitButton = getButton('button[type="submit"]');
    expect(submitButton.disabled).toBe(false);
    submitButton.click();

    expect(serviceSpy.calls.mostRecent().args).toEqual([username, password]);
  });

  it('should disable sumbit if username is invalid', () => {
    fixture.detectChanges();
    setInputValue('#username', 'john.doe'); // invalid email
    setInputValue('#password', password);
    fixture.detectChanges();

    const submitButton = getButton('button[type="submit"]');
    expect(submitButton.disabled).toBe(true);
  });

  it('should disable sumbit if password is invalid', () => {
    fixture.detectChanges();
    setInputValue('#username', username);
    setInputValue('#password', ''); // password is required
    fixture.detectChanges();

    const submitButton = getButton('button[type="submit"]');
    expect(submitButton.disabled).toBe(true);
  });

  function getInput(selector: string) {
    return nativeElement.querySelector<HTMLInputElement>(selector);
  }

  function getButton(selector: string) {
    return nativeElement.querySelector<HTMLButtonElement>(selector);
  }

  function setInputValue(selector: string, value) {
    const inputElement = getInput(selector);
    inputElement.value = value;
    inputElement.dispatchEvent(new Event('input'));
  }
});
