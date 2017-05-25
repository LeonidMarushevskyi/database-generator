import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EthnicityType } from './ethnicity-type.model';
import { EthnicityTypeService } from './ethnicity-type.service';
@Injectable()
export class EthnicityTypePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ethnicityTypeService: EthnicityTypeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.ethnicityTypeService.find(id).subscribe((ethnicityType) => {
                this.ethnicityTypeModalRef(component, ethnicityType);
            });
        } else {
            return this.ethnicityTypeModalRef(component, new EthnicityType());
        }
    }

    ethnicityTypeModalRef(component: Component, ethnicityType: EthnicityType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ethnicityType = ethnicityType;
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
