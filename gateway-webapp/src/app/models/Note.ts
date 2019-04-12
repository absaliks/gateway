export class Note {
  id: number;
  title: string;
  text: string;

  constructor(id: number, title: string, text: string) {
    this.id = id;
    this.title = title;
    this.text = text;
  }
}

export const NULL_NOTE: Note = new Note(0, '', '');
