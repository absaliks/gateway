import {Component, OnInit} from '@angular/core';
import {NoteService} from '../services/note.service';
import {Note} from '../models/Note';
import {MatSnackBar} from '@angular/material';
import {Observable} from "rxjs";
import {BreakpointObserver, Breakpoints} from "@angular/cdk/layout";
import {map} from "rxjs/operators";

@Component({
  selector: 'app-note-list',
  templateUrl: './note-list.component.html',
  styleUrls: ['./note-list.component.scss'],
  providers: [NoteService]
})
export class NoteListComponent implements OnInit {
  static readonly ROUTER_PATH = 'notes';

  isHandset$: Observable<boolean> =
    this.breakpointObserver.observe(Breakpoints.Handset).pipe(map(result => result.matches));

  title = '';
  notes: Note[] = [];

  constructor(private breakpointObserver: BreakpointObserver,
              private service: NoteService,
              private snackBar: MatSnackBar) {
  }

  ngOnInit(): void {
    this.service.getNotes().subscribe(notes => this.notes = notes);
  }

  addNote() {
    if (this.title && !this.notes.some(note => note.title === this.title)) {
      this.service.addNote(new Note(null, this.title, '')).subscribe(managedNote => {
        if (managedNote.id > 0) {
          this.notes.push(managedNote);
        } else {
          this.snackBar.open("Unable to add the note " + this.title);
        }
      });
    }
  }
}
