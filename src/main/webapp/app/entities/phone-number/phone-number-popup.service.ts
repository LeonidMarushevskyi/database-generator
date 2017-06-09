import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PhoneNumber } from './phone-number.model';
import { PhoneNumberService } from './phone-number.service';
@Injectable()
export class PhoneNumberPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private phoneNumberService: PhoneNumberService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.phoneNumberService.find(id).subscribe((phoneNumber) => {
                phoneNumber.createDateTime = this.datePipe
                    .transform(phoneNumber.createDateTime, 'yyyy-MM-ddThh:mm');
                phoneNumber.updateDateTime = this.datePipe
                    .transform(phoneNumber.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.phoneNumberModalRef(component, phoneNumber);
            });
        } else {
            return this.phoneNumberModalRef(component, new PhoneNumber());
        }
    }

    phoneNumberModalRef(component: Component, phoneNumber: PhoneNumber): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.phoneNumber = phoneNumber;
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
