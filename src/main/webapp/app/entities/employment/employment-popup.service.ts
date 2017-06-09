import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Employment } from './employment.model';
import { EmploymentService } from './employment.service';
@Injectable()
export class EmploymentPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private employmentService: EmploymentService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.employmentService.find(id).subscribe((employment) => {
                if (employment.startDate) {
                    employment.startDate = {
                        year: employment.startDate.getFullYear(),
                        month: employment.startDate.getMonth() + 1,
                        day: employment.startDate.getDate()
                    };
                }
                if (employment.endDate) {
                    employment.endDate = {
                        year: employment.endDate.getFullYear(),
                        month: employment.endDate.getMonth() + 1,
                        day: employment.endDate.getDate()
                    };
                }
                employment.createDateTime = this.datePipe
                    .transform(employment.createDateTime, 'yyyy-MM-ddThh:mm');
                employment.updateDateTime = this.datePipe
                    .transform(employment.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.employmentModalRef(component, employment);
            });
        } else {
            return this.employmentModalRef(component, new Employment());
        }
    }

    employmentModalRef(component: Component, employment: Employment): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.employment = employment;
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
