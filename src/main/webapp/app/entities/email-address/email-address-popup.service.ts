import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { EmailAddress } from './email-address.model';
import { EmailAddressService } from './email-address.service';
@Injectable()
export class EmailAddressPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private emailAddressService: EmailAddressService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.emailAddressService.find(id).subscribe((emailAddress) => {
                emailAddress.createDateTime = this.datePipe
                    .transform(emailAddress.createDateTime, 'yyyy-MM-ddThh:mm');
                emailAddress.updateDateTime = this.datePipe
                    .transform(emailAddress.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.emailAddressModalRef(component, emailAddress);
            });
        } else {
            return this.emailAddressModalRef(component, new EmailAddress());
        }
    }

    emailAddressModalRef(component: Component, emailAddress: EmailAddress): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.emailAddress = emailAddress;
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
