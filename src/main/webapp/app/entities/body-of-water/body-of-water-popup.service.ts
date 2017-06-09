import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { BodyOfWater } from './body-of-water.model';
import { BodyOfWaterService } from './body-of-water.service';
@Injectable()
export class BodyOfWaterPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private bodyOfWaterService: BodyOfWaterService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.bodyOfWaterService.find(id).subscribe((bodyOfWater) => {
                bodyOfWater.createDateTime = this.datePipe
                    .transform(bodyOfWater.createDateTime, 'yyyy-MM-ddThh:mm');
                bodyOfWater.updateDateTime = this.datePipe
                    .transform(bodyOfWater.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.bodyOfWaterModalRef(component, bodyOfWater);
            });
        } else {
            return this.bodyOfWaterModalRef(component, new BodyOfWater());
        }
    }

    bodyOfWaterModalRef(component: Component, bodyOfWater: BodyOfWater): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.bodyOfWater = bodyOfWater;
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
