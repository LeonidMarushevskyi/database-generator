import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PersonAddress } from './person-address.model';
import { PersonAddressService } from './person-address.service';
@Injectable()
export class PersonAddressPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private personAddressService: PersonAddressService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.personAddressService.find(id).subscribe((personAddress) => {
                personAddress.createDateTime = this.datePipe
                    .transform(personAddress.createDateTime, 'yyyy-MM-ddThh:mm');
                personAddress.updateDateTime = this.datePipe
                    .transform(personAddress.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.personAddressModalRef(component, personAddress);
            });
        } else {
            return this.personAddressModalRef(component, new PersonAddress());
        }
    }

    personAddressModalRef(component: Component, personAddress: PersonAddress): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.personAddress = personAddress;
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
