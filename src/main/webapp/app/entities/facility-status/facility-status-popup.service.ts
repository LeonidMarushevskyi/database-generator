import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FacilityStatus } from './facility-status.model';
import { FacilityStatusService } from './facility-status.service';
@Injectable()
export class FacilityStatusPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private facilityStatusService: FacilityStatusService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.facilityStatusService.find(id).subscribe((facilityStatus) => {
                this.facilityStatusModalRef(component, facilityStatus);
            });
        } else {
            return this.facilityStatusModalRef(component, new FacilityStatus());
        }
    }

    facilityStatusModalRef(component: Component, facilityStatus: FacilityStatus): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.facilityStatus = facilityStatus;
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
