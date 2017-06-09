import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PosessionType } from './posession-type.model';
import { PosessionTypeService } from './posession-type.service';
@Injectable()
export class PosessionTypePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private posessionTypeService: PosessionTypeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.posessionTypeService.find(id).subscribe((posessionType) => {
                this.posessionTypeModalRef(component, posessionType);
            });
        } else {
            return this.posessionTypeModalRef(component, new PosessionType());
        }
    }

    posessionTypeModalRef(component: Component, posessionType: PosessionType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.posessionType = posessionType;
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
