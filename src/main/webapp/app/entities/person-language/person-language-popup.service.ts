import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PersonLanguage } from './person-language.model';
import { PersonLanguageService } from './person-language.service';
@Injectable()
export class PersonLanguagePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private personLanguageService: PersonLanguageService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.personLanguageService.find(id).subscribe((personLanguage) => {
                this.personLanguageModalRef(component, personLanguage);
            });
        } else {
            return this.personLanguageModalRef(component, new PersonLanguage());
        }
    }

    personLanguageModalRef(component: Component, personLanguage: PersonLanguage): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.personLanguage = personLanguage;
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
