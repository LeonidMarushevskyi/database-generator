import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Household } from './household.model';
import { HouseholdService } from './household.service';
@Injectable()
export class HouseholdPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private householdService: HouseholdService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.householdService.find(id).subscribe((household) => {
                household.createDateTime = this.datePipe
                    .transform(household.createDateTime, 'yyyy-MM-ddThh:mm');
                household.updateDateTime = this.datePipe
                    .transform(household.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.householdModalRef(component, household);
            });
        } else {
            return this.householdModalRef(component, new Household());
        }
    }

    householdModalRef(component: Component, household: Household): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.household = household;
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
