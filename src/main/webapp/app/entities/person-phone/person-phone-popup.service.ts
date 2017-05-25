import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PersonPhone } from './person-phone.model';
import { PersonPhoneService } from './person-phone.service';
@Injectable()
export class PersonPhonePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private personPhoneService: PersonPhoneService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.personPhoneService.find(id).subscribe((personPhone) => {
                this.personPhoneModalRef(component, personPhone);
            });
        } else {
            return this.personPhoneModalRef(component, new PersonPhone());
        }
    }

    personPhoneModalRef(component: Component, personPhone: PersonPhone): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.personPhone = personPhone;
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
