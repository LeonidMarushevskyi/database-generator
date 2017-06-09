import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { ApplicationStatusType } from './application-status-type.model';
import { ApplicationStatusTypeService } from './application-status-type.service';
@Injectable()
export class ApplicationStatusTypePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private applicationStatusTypeService: ApplicationStatusTypeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.applicationStatusTypeService.find(id).subscribe((applicationStatusType) => {
                this.applicationStatusTypeModalRef(component, applicationStatusType);
            });
        } else {
            return this.applicationStatusTypeModalRef(component, new ApplicationStatusType());
        }
    }

    applicationStatusTypeModalRef(component: Component, applicationStatusType: ApplicationStatusType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.applicationStatusType = applicationStatusType;
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
