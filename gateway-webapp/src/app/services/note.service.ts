import {Injectable} from "@angular/core";
import {Note, NULL_NOTE} from '../models/Note';
import {Observable, of} from "rxjs";
import {HttpClient} from '@angular/common/http';
import {catchError} from 'rxjs/operators';
import {Notebook} from "../models/Notebook";

@Injectable()
export class NoteService {

  private readonly api_url = "http://localhost:8080";

  constructor(private http: HttpClient) {
  }

  getNotebooks(): Observable<Notebook[]> {
    let devNotebook = createNotebook("DEV", 1);
    return of([
      createNotebook('Default notebook'),
      devNotebook,
      createNotebook('Java', 2, 1),
      createNotebook('Spring', 6, 2),
      createNotebook('Another one', 3),
      createNotebook('TypeScript', 5, 1)
    ]);
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

function createNotebook(name: string, id = 0, parentId = null): Notebook {
  return {id, parentId, name};
}
