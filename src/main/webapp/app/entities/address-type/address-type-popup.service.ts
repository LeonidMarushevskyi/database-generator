import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AddressType } from './address-type.model';
import { AddressTypeService } from './address-type.service';
@Injectable()
export class AddressTypePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private addressTypeService: AddressTypeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.addressTypeService.find(id).subscribe((addressType) => {
                this.addressTypeModalRef(component, addressType);
            });
        } else {
            return this.addressTypeModalRef(component, new AddressType());
        }
    }

    addressTypeModalRef(component: Component, addressType: AddressType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.addressType = addressType;
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
