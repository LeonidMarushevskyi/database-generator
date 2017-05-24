import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LanguageType } from './language-type.model';
import { LanguageTypeService } from './language-type.service';
@Injectable()
export class LanguageTypePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private languageTypeService: LanguageTypeService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.languageTypeService.find(id).subscribe(languageType => {
                this.languageTypeModalRef(component, languageType);
            });
        } else {
            return this.languageTypeModalRef(component, new LanguageType());
        }
    }

    languageTypeModalRef(component: Component, languageType: LanguageType): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.languageType = languageType;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
