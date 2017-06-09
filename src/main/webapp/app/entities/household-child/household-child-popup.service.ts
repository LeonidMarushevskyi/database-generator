import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { HouseholdChild } from './household-child.model';
import { HouseholdChildService } from './household-child.service';
@Injectable()
export class HouseholdChildPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private householdChildService: HouseholdChildService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.householdChildService.find(id).subscribe((householdChild) => {
                householdChild.createDateTime = this.datePipe
                    .transform(householdChild.createDateTime, 'yyyy-MM-ddThh:mm');
                householdChild.updateDateTime = this.datePipe
                    .transform(householdChild.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.householdChildModalRef(component, householdChild);
            });
        } else {
            return this.householdChildModalRef(component, new HouseholdChild());
        }
    }

    householdChildModalRef(component: Component, householdChild: HouseholdChild): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.householdChild = householdChild;
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
