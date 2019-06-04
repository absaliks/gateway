import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {NoteListComponent} from './note-list/note-list.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';
import {
  MatButtonModule,
  MatFormFieldModule,
  MatIconModule,
  MatInputModule,
  MatListModule,
  MatSidenavModule,
  MatSnackBarModule,
  MatToolbarModule,
  MatTreeModule
} from '@angular/material';
import {FormsModule} from '@angular/forms';
import {HttpClientModule} from '@angular/common/http';
import {LayoutModule} from '@angular/cdk/layout';
import {NotebookTreeComponent} from './notebook-tree/notebook-tree.component';

@NgModule({
  declarations: [
    AppComponent,
    NoteListComponent,
    NotebookTreeComponent
  ],
  imports: [
    AppRoutingModule,
    BrowserAnimationsModule,
    BrowserModule,
    FormsModule,
    HttpClientModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSnackBarModule,
    LayoutModule,
    MatIconModule,
    MatListModule,
    MatSidenavModule,
    MatToolbarModule,
    MatTreeModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule {
}
