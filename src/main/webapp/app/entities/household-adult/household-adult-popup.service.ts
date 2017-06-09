import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { HouseholdAdult } from './household-adult.model';
import { HouseholdAdultService } from './household-adult.service';
@Injectable()
export class HouseholdAdultPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private householdAdultService: HouseholdAdultService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.householdAdultService.find(id).subscribe((householdAdult) => {
                householdAdult.createDateTime = this.datePipe
                    .transform(householdAdult.createDateTime, 'yyyy-MM-ddThh:mm');
                householdAdult.updateDateTime = this.datePipe
                    .transform(householdAdult.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.householdAdultModalRef(component, householdAdult);
            });
        } else {
            return this.householdAdultModalRef(component, new HouseholdAdult());
        }
    }

    householdAdultModalRef(component: Component, householdAdult: HouseholdAdult): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.householdAdult = householdAdult;
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
