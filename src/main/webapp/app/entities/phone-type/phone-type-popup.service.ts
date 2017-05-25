import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PhoneType } from './phone-type.model';
import { PhoneTypeService } from './phone-type.service';
@Injectable()
export class PhoneTypePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private phoneTypeService: PhoneTypeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.phoneTypeService.find(id).subscribe((phoneType) => {
                this.phoneTypeModalRef(component, phoneType);
            });
        } else {
            return this.phoneTypeModalRef(component, new PhoneType());
        }
    }

    phoneTypeModalRef(component: Component, phoneType: PhoneType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.phoneType = phoneType;
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
