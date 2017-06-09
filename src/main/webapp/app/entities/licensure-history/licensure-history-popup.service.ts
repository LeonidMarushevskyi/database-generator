import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { LicensureHistory } from './licensure-history.model';
import { LicensureHistoryService } from './licensure-history.service';
@Injectable()
export class LicensureHistoryPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private licensureHistoryService: LicensureHistoryService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.licensureHistoryService.find(id).subscribe((licensureHistory) => {
                this.licensureHistoryModalRef(component, licensureHistory);
            });
        } else {
            return this.licensureHistoryModalRef(component, new LicensureHistory());
        }
    }

    licensureHistoryModalRef(component: Component, licensureHistory: LicensureHistory): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.licensureHistory = licensureHistory;
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
