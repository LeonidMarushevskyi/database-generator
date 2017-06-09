import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { EducationPoint } from './education-point.model';
import { EducationPointService } from './education-point.service';
@Injectable()
export class EducationPointPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private educationPointService: EducationPointService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.educationPointService.find(id).subscribe((educationPoint) => {
                educationPoint.createDateTime = this.datePipe
                    .transform(educationPoint.createDateTime, 'yyyy-MM-ddThh:mm');
                educationPoint.updateDateTime = this.datePipe
                    .transform(educationPoint.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.educationPointModalRef(component, educationPoint);
            });
        } else {
            return this.educationPointModalRef(component, new EducationPoint());
        }
    }

    educationPointModalRef(component: Component, educationPoint: EducationPoint): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.educationPoint = educationPoint;
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
