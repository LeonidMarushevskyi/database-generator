import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Facility } from './facility.model';
import { FacilityService } from './facility.service';
@Injectable()
export class FacilityPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private facilityService: FacilityService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.facilityService.find(id).subscribe((facility) => {
                if (facility.licenseEffectiveDate) {
                    facility.licenseEffectiveDate = {
                        year: facility.licenseEffectiveDate.getFullYear(),
                        month: facility.licenseEffectiveDate.getMonth() + 1,
                        day: facility.licenseEffectiveDate.getDate()
                    };
                }
                if (facility.originalApplicationRecievedDate) {
                    facility.originalApplicationRecievedDate = {
                        year: facility.originalApplicationRecievedDate.getFullYear(),
                        month: facility.originalApplicationRecievedDate.getMonth() + 1,
                        day: facility.originalApplicationRecievedDate.getDate()
                    };
                }
                if (facility.lastVisitDate) {
                    facility.lastVisitDate = {
                        year: facility.lastVisitDate.getFullYear(),
                        month: facility.lastVisitDate.getMonth() + 1,
                        day: facility.lastVisitDate.getDate()
                    };
                }
                this.facilityModalRef(component, facility);
            });
        } else {
            return this.facilityModalRef(component, new Facility());
        }
    }

    facilityModalRef(component: Component, facility: Facility): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.facility = facility;
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
