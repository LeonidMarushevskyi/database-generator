import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Application } from './application.model';
import { ApplicationService } from './application.service';
@Injectable()
export class ApplicationPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private applicationService: ApplicationService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.applicationService.find(id).subscribe((application) => {
                application.createDateTime = this.datePipe
                    .transform(application.createDateTime, 'yyyy-MM-ddThh:mm');
                application.updateDateTime = this.datePipe
                    .transform(application.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.applicationModalRef(component, application);
            });
        } else {
            return this.applicationModalRef(component, new Application());
        }
    }

    applicationModalRef(component: Component, application: Application): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.application = application;
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
