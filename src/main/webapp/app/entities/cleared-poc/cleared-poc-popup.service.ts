import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { ClearedPOC } from './cleared-poc.model';
import { ClearedPOCService } from './cleared-poc.service';
@Injectable()
export class ClearedPOCPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private clearedPOCService: ClearedPOCService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.clearedPOCService.find(id).subscribe((clearedPOC) => {
                clearedPOC.pocduedate = this.datePipe
                    .transform(clearedPOC.pocduedate, 'yyyy-MM-ddThh:mm');
                clearedPOC.pocdatecleared = this.datePipe
                    .transform(clearedPOC.pocdatecleared, 'yyyy-MM-ddThh:mm');
                this.clearedPOCModalRef(component, clearedPOC);
            });
        } else {
            return this.clearedPOCModalRef(component, new ClearedPOC());
        }
    }

    clearedPOCModalRef(component: Component, clearedPOC: ClearedPOC): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.clearedPOC = clearedPOC;
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
