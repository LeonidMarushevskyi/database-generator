import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { PersonRace } from './person-race.model';
import { PersonRaceService } from './person-race.service';
@Injectable()
export class PersonRacePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private personRaceService: PersonRaceService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.personRaceService.find(id).subscribe((personRace) => {
                this.personRaceModalRef(component, personRace);
            });
        } else {
            return this.personRaceModalRef(component, new PersonRace());
        }
    }

    personRaceModalRef(component: Component, personRace: PersonRace): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.personRace = personRace;
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
