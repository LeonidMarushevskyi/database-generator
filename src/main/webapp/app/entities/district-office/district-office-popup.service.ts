import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DistrictOffice } from './district-office.model';
import { DistrictOfficeService } from './district-office.service';
@Injectable()
export class DistrictOfficePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private districtOfficeService: DistrictOfficeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.districtOfficeService.find(id).subscribe((districtOffice) => {
                this.districtOfficeModalRef(component, districtOffice);
            });
        } else {
            return this.districtOfficeModalRef(component, new DistrictOffice());
        }
    }

    districtOfficeModalRef(component: Component, districtOffice: DistrictOffice): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.districtOffice = districtOffice;
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
