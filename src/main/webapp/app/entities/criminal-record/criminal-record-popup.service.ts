import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { CriminalRecord } from './criminal-record.model';
import { CriminalRecordService } from './criminal-record.service';
@Injectable()
export class CriminalRecordPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private criminalRecordService: CriminalRecordService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.criminalRecordService.find(id).subscribe((criminalRecord) => {
                if (criminalRecord.offenseDate) {
                    criminalRecord.offenseDate = {
                        year: criminalRecord.offenseDate.getFullYear(),
                        month: criminalRecord.offenseDate.getMonth() + 1,
                        day: criminalRecord.offenseDate.getDate()
                    };
                }
                criminalRecord.createDateTime = this.datePipe
                    .transform(criminalRecord.createDateTime, 'yyyy-MM-ddThh:mm');
                criminalRecord.updateDateTime = this.datePipe
                    .transform(criminalRecord.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.criminalRecordModalRef(component, criminalRecord);
            });
        } else {
            return this.criminalRecordModalRef(component, new CriminalRecord());
        }
    }

    criminalRecordModalRef(component: Component, criminalRecord: CriminalRecord): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.criminalRecord = criminalRecord;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
