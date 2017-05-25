import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FacilityPhone } from './facility-phone.model';
import { FacilityPhoneService } from './facility-phone.service';
@Injectable()
export class FacilityPhonePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private facilityPhoneService: FacilityPhoneService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.facilityPhoneService.find(id).subscribe((facilityPhone) => {
                this.facilityPhoneModalRef(component, facilityPhone);
            });
        } else {
            return this.facilityPhoneModalRef(component, new FacilityPhone());
        }
    }

    facilityPhoneModalRef(component: Component, facilityPhone: FacilityPhone): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.facilityPhone = facilityPhone;
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
