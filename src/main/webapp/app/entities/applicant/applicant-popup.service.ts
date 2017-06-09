import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Applicant } from './applicant.model';
import { ApplicantService } from './applicant.service';
@Injectable()
export class ApplicantPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private applicantService: ApplicantService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.applicantService.find(id).subscribe((applicant) => {
                applicant.createDateTime = this.datePipe
                    .transform(applicant.createDateTime, 'yyyy-MM-ddThh:mm');
                applicant.updateDateTime = this.datePipe
                    .transform(applicant.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.applicantModalRef(component, applicant);
            });
        } else {
            return this.applicantModalRef(component, new Applicant());
        }
    }

    applicantModalRef(component: Component, applicant: Applicant): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.applicant = applicant;
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
