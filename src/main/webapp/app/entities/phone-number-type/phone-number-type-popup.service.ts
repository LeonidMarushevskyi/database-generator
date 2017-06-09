import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PhoneNumberType } from './phone-number-type.model';
import { PhoneNumberTypeService } from './phone-number-type.service';
@Injectable()
export class PhoneNumberTypePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private phoneNumberTypeService: PhoneNumberTypeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.phoneNumberTypeService.find(id).subscribe((phoneNumberType) => {
                this.phoneNumberTypeModalRef(component, phoneNumberType);
            });
        } else {
            return this.phoneNumberTypeModalRef(component, new PhoneNumberType());
        }
    }

    phoneNumberTypeModalRef(component: Component, phoneNumberType: PhoneNumberType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.phoneNumberType = phoneNumberType;
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
