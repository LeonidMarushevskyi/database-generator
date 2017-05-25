import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Deficiency } from './deficiency.model';
import { DeficiencyService } from './deficiency.service';
@Injectable()
export class DeficiencyPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private deficiencyService: DeficiencyService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.deficiencyService.find(id).subscribe((deficiency) => {
                deficiency.pocDate = this.datePipe
                    .transform(deficiency.pocDate, 'yyyy-MM-ddThh:mm');
                this.deficiencyModalRef(component, deficiency);
            });
        } else {
            return this.deficiencyModalRef(component, new Deficiency());
        }
    }

    deficiencyModalRef(component: Component, deficiency: Deficiency): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.deficiency = deficiency;
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
