import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EducationLevelType } from './education-level-type.model';
import { EducationLevelTypeService } from './education-level-type.service';
@Injectable()
export class EducationLevelTypePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private educationLevelTypeService: EducationLevelTypeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.educationLevelTypeService.find(id).subscribe((educationLevelType) => {
                this.educationLevelTypeModalRef(component, educationLevelType);
            });
        } else {
            return this.educationLevelTypeModalRef(component, new EducationLevelType());
        }
    }

    educationLevelTypeModalRef(component: Component, educationLevelType: EducationLevelType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.educationLevelType = educationLevelType;
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
