import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PersonEthnicity } from './person-ethnicity.model';
import { PersonEthnicityService } from './person-ethnicity.service';
@Injectable()
export class PersonEthnicityPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private personEthnicityService: PersonEthnicityService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.personEthnicityService.find(id).subscribe((personEthnicity) => {
                this.personEthnicityModalRef(component, personEthnicity);
            });
        } else {
            return this.personEthnicityModalRef(component, new PersonEthnicity());
        }
    }

    personEthnicityModalRef(component: Component, personEthnicity: PersonEthnicity): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.personEthnicity = personEthnicity;
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
