import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { PersonPreviousName } from './person-previous-name.model';
import { PersonPreviousNameService } from './person-previous-name.service';
@Injectable()
export class PersonPreviousNamePopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private personPreviousNameService: PersonPreviousNameService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.personPreviousNameService.find(id).subscribe((personPreviousName) => {
                personPreviousName.createDateTime = this.datePipe
                    .transform(personPreviousName.createDateTime, 'yyyy-MM-ddThh:mm');
                personPreviousName.updateDateTime = this.datePipe
                    .transform(personPreviousName.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.personPreviousNameModalRef(component, personPreviousName);
            });
        } else {
            return this.personPreviousNameModalRef(component, new PersonPreviousName());
        }
    }

    personPreviousNameModalRef(component: Component, personPreviousName: PersonPreviousName): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.personPreviousName = personPreviousName;
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
