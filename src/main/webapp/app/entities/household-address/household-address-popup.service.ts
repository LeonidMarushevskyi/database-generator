import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { HouseholdAddress } from './household-address.model';
import { HouseholdAddressService } from './household-address.service';
@Injectable()
export class HouseholdAddressPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private householdAddressService: HouseholdAddressService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.householdAddressService.find(id).subscribe((householdAddress) => {
                householdAddress.createDateTime = this.datePipe
                    .transform(householdAddress.createDateTime, 'yyyy-MM-ddThh:mm');
                householdAddress.updateDateTime = this.datePipe
                    .transform(householdAddress.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.householdAddressModalRef(component, householdAddress);
            });
        } else {
            return this.householdAddressModalRef(component, new HouseholdAddress());
        }
    }

    householdAddressModalRef(component: Component, householdAddress: HouseholdAddress): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.householdAddress = householdAddress;
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
