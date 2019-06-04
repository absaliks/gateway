import {Component, OnInit} from '@angular/core';
import {Notebook} from "../models/Notebook";
import {NoteService} from "../services/note.service";
import {FlatTreeControl} from "@angular/cdk/tree";
import {MatTreeFlatDataSource, MatTreeFlattener} from "@angular/material";

interface NotebookNode extends Notebook {
  children?: NotebookNode[];
}

interface ExpandableFlatNode {
  expandable: boolean;
  name: string;
  level: number;
}

@Component({
  selector: 'app-notebook-tree',
  templateUrl: './notebook-tree.component.html',
  styleUrls: ['./notebook-tree.component.scss']
})
export class NotebookTreeComponent implements OnInit {

  private transformer = (node: NotebookNode, level: number) => {
    return {
      expandable: !!node.children && node.children.length > 0,
      name: node.name,
      level: level,
    };
  };

  treeControl = new FlatTreeControl<ExpandableFlatNode>(
    node => node.level, node => node.expandable);
  treeFlattener = new MatTreeFlattener(
    this.transformer, node => node.level, node => node.expandable, node => node.children);
  dataSource = new MatTreeFlatDataSource(this.treeControl, this.treeFlattener);

  constructor(private service: NoteService) {
  }

  ngOnInit(): void {
    this.service.getNotebooks().subscribe(notebooks =>
      this.dataSource.data = this.mapNotebooksToNodes(notebooks));
  }

  private mapNotebooksToNodes(notebooks: Notebook[]): NotebookNode[] {
    const rootNodes: NotebookNode[] = [];
    const nonRootNotebooks: Notebook[] = [];
    notebooks.forEach(item => (!item.parentId ? rootNodes : nonRootNotebooks).push(item));
    this.mapNotebooksToGivenNodes(rootNodes, nonRootNotebooks);
    return rootNodes;
  }

  private mapNotebooksToGivenNodes(parentNodes: NotebookNode[], notebooks: Notebook[]) {
    if (!notebooks || !notebooks.length) {
      return
    }
    for (let i = 0; i < parentNodes.length; i++) {
      let parentNode = parentNodes[i];
      const children: NotebookNode[] = [];
      const unmappedNotebooks: Notebook[] = [];
      notebooks.forEach(notebook =>
        (parentNode.id === notebook.parentId ? children : unmappedNotebooks).push(notebook));
      parentNode.children = children;
      if (notebooks.length === 0) {
        break;
      }
      this.mapNotebooksToGivenNodes(children, unmappedNotebooks);
    }
  }

  hasChild = (_: number, node: ExpandableFlatNode) => node.expandable;
}
