import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { FacilityChild } from './facility-child.model';
import { FacilityChildService } from './facility-child.service';
@Injectable()
export class FacilityChildPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private facilityChildService: FacilityChildService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.facilityChildService.find(id).subscribe((facilityChild) => {
                if (facilityChild.dateOfPlacement) {
                    facilityChild.dateOfPlacement = {
                        year: facilityChild.dateOfPlacement.getFullYear(),
                        month: facilityChild.dateOfPlacement.getMonth() + 1,
                        day: facilityChild.dateOfPlacement.getDate()
                    };
                }
                this.facilityChildModalRef(component, facilityChild);
            });
        } else {
            return this.facilityChildModalRef(component, new FacilityChild());
        }
    }

    facilityChildModalRef(component: Component, facilityChild: FacilityChild): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.facilityChild = facilityChild;
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
