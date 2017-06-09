import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ChildPreferences } from './child-preferences.model';
import { ChildPreferencesService } from './child-preferences.service';
@Injectable()
export class ChildPreferencesPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private childPreferencesService: ChildPreferencesService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.childPreferencesService.find(id).subscribe((childPreferences) => {
                childPreferences.createDateTime = this.datePipe
                    .transform(childPreferences.createDateTime, 'yyyy-MM-ddThh:mm');
                childPreferences.updateDateTime = this.datePipe
                    .transform(childPreferences.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.childPreferencesModalRef(component, childPreferences);
            });
        } else {
            return this.childPreferencesModalRef(component, new ChildPreferences());
        }
    }

    childPreferencesModalRef(component: Component, childPreferences: ChildPreferences): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.childPreferences = childPreferences;
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
