import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SiblingGroupType } from './sibling-group-type.model';
import { SiblingGroupTypeService } from './sibling-group-type.service';
@Injectable()
export class SiblingGroupTypePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private siblingGroupTypeService: SiblingGroupTypeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.siblingGroupTypeService.find(id).subscribe((siblingGroupType) => {
                this.siblingGroupTypeModalRef(component, siblingGroupType);
            });
        } else {
            return this.siblingGroupTypeModalRef(component, new SiblingGroupType());
        }
    }

    siblingGroupTypeModalRef(component: Component, siblingGroupType: SiblingGroupType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.siblingGroupType = siblingGroupType;
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
