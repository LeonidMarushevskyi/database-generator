import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FacilityAddress } from './facility-address.model';
import { FacilityAddressService } from './facility-address.service';
@Injectable()
export class FacilityAddressPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private facilityAddressService: FacilityAddressService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.facilityAddressService.find(id).subscribe(facilityAddress => {
                this.facilityAddressModalRef(component, facilityAddress);
            });
        } else {
            return this.facilityAddressModalRef(component, new FacilityAddress());
        }
    }

    facilityAddressModalRef(component: Component, facilityAddress: FacilityAddress): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.facilityAddress = facilityAddress;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
