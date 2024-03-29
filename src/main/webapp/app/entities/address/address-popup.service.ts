import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Address } from './address.model';
import { AddressService } from './address.service';
@Injectable()
export class AddressPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private addressService: AddressService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.addressService.find(id).subscribe((address) => {
                address.createDateTime = this.datePipe
                    .transform(address.createDateTime, 'yyyy-MM-ddThh:mm');
                address.updateDateTime = this.datePipe
                    .transform(address.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.addressModalRef(component, address);
            });
        } else {
            return this.addressModalRef(component, new Address());
        }
    }

    addressModalRef(component: Component, address: Address): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.address = address;
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
