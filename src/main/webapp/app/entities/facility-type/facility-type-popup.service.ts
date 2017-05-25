import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FacilityType } from './facility-type.model';
import { FacilityTypeService } from './facility-type.service';
@Injectable()
export class FacilityTypePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private facilityTypeService: FacilityTypeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.facilityTypeService.find(id).subscribe((facilityType) => {
                this.facilityTypeModalRef(component, facilityType);
            });
        } else {
            return this.facilityTypeModalRef(component, new FacilityType());
        }
    }

    facilityTypeModalRef(component: Component, facilityType: FacilityType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.facilityType = facilityType;
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
