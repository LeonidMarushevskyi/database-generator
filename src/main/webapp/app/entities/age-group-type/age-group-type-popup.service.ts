import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AgeGroupType } from './age-group-type.model';
import { AgeGroupTypeService } from './age-group-type.service';
@Injectable()
export class AgeGroupTypePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private ageGroupTypeService: AgeGroupTypeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.ageGroupTypeService.find(id).subscribe((ageGroupType) => {
                this.ageGroupTypeModalRef(component, ageGroupType);
            });
        } else {
            return this.ageGroupTypeModalRef(component, new AgeGroupType());
        }
    }

    ageGroupTypeModalRef(component: Component, ageGroupType: AgeGroupType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.ageGroupType = ageGroupType;
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
