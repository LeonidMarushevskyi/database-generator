import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RaceType } from './race-type.model';
import { RaceTypeService } from './race-type.service';
@Injectable()
export class RaceTypePopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private raceTypeService: RaceTypeService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.raceTypeService.find(id).subscribe(raceType => {
                this.raceTypeModalRef(component, raceType);
            });
        } else {
            return this.raceTypeModalRef(component, new RaceType());
        }
    }

    raceTypeModalRef(component: Component, raceType: RaceType): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.raceType = raceType;
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
