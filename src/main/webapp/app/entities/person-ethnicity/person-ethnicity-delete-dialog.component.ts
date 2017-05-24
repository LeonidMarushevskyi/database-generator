import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { PersonEthnicity } from './person-ethnicity.model';
import { PersonEthnicityPopupService } from './person-ethnicity-popup.service';
import { PersonEthnicityService } from './person-ethnicity.service';

@Component({
    selector: 'jhi-person-ethnicity-delete-dialog',
    templateUrl: './person-ethnicity-delete-dialog.component.html'
})
export class PersonEthnicityDeleteDialogComponent {

    personEthnicity: PersonEthnicity;

    constructor(
        private personEthnicityService: PersonEthnicityService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.personEthnicityService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'personEthnicityListModification',
                content: 'Deleted an personEthnicity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-person-ethnicity-delete-popup',
    template: ''
})
export class PersonEthnicityDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private personEthnicityPopupService: PersonEthnicityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.personEthnicityPopupService
                .open(PersonEthnicityDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
