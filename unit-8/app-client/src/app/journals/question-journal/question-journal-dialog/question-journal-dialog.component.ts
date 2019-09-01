import {ChangeDetectionStrategy, Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {QuestionJournalItem} from 'src/app/model/question-journal-item.model';

export interface JournalData {
  journalItem: QuestionJournalItem;
  eventLabel: string;
  answersCountItems: number[];
}

export interface QuestionJournalDialogResult {
  name: string;
  answersCount: number;
}

@Component({
  selector: 'app-question-journal-dialog',
  templateUrl: './question-journal-dialog.component.html',
  styleUrls: ['./question-journal-dialog.component.css'],
  changeDetection: ChangeDetectionStrategy.OnPush,
})

export class QuestionJournalDialogComponent implements OnInit {

  public eventLabel: string;
  public answersCountItems: number[];
  public dialogForm: FormGroup;

  private description = '';
  private answersCount = 0;

  constructor(public dialogRef: MatDialogRef<QuestionJournalDialogComponent>,
              private fb: FormBuilder,
              @Inject(MAT_DIALOG_DATA) public data: JournalData) {
  }

  ngOnInit() {
    this.fillData();
    this.fillForm();
  }

  private fillData() {
    this.eventLabel = this.data.eventLabel;
    this.answersCountItems = this.data.answersCountItems;

    if (this.data.journalItem) {
      this.description = this.data.journalItem.name;
      this.answersCount = this.data.journalItem.answersCount;
    }
  }

  private fillForm() {
    this.dialogForm = this.fb.group({
      description: new FormControl(this.description, Validators.required),
      answersCount: new FormControl(this.answersCount),
    });
  }

  onDialogSubmit() {
    let newValues: QuestionJournalDialogResult;
    const newDescription: string = this.dialogForm.get('description').value;
    const newAnswersCount: number = this.dialogForm.get('answersCount').value;

    if (newDescription !== this.description || newAnswersCount !== this.answersCount) {
      newValues = {
        name: newDescription,
        answersCount: newAnswersCount,
      };
    }
    this.dialogRef.close(newValues);
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
