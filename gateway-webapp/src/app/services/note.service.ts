import {Injectable} from "@angular/core";
import { NULL_NOTE, Note } from '../models/Note';
import {Observable, of} from "rxjs";
import { HttpClient } from '@angular/common/http';
import { catchError } from 'rxjs/operators';

@Injectable()
export class NoteService {

  private readonly api_url = "http://localhost:8080";

  constructor(private http: HttpClient) {
  }

  getNotes(): Observable<Note[]> {
    return this.http.get<Note[]>(this.api_url + "/notes")
      .pipe(
        catchError(this.handleError('getNotes', []))
      );
  }

  private handleError<T>(operation: string, result?: T) {
    return (error: any) => {
      console.error(operation + ' failed: ' + error.message);
      return of(result as T);
    };
  }

  addNote(note: Note): Observable<Note>  {
    return this.http.post<Note>(this.api_url + "/notes", note)
      .pipe(
        catchError(this.handleError('getNotes', NULL_NOTE))
      );
  }
}
