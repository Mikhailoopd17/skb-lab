import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';

@Component({
  selector: 'app-message',
  templateUrl: './message.component.html',
  styleUrls: ['./message.component.css']
})
export class MessageComponent implements OnInit {
  message: string;
  constructor(@Inject(MAT_DIALOG_DATA) public data: string,
              public dialogRef: MatDialogRef<MessageComponent>,
  ) {
    this.message = data;
  }

  ngOnInit(): void {
  }

  close(): void {
    this.dialogRef.close();
  }

}
