import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { PersonPhone } from './person-phone.model';
import { PersonPhonePopupService } from './person-phone-popup.service';
import { PersonPhoneService } from './person-phone.service';

@Component({
    selector: 'jhi-person-phone-delete-dialog',
    templateUrl: './person-phone-delete-dialog.component.html'
})
export class PersonPhoneDeleteDialogComponent {

    personPhone: PersonPhone;

    constructor(
        private personPhoneService: PersonPhoneService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personPhoneService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'personPhoneListModification',
                content: 'Deleted an personPhone'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-person-phone-delete-popup',
    template: ''
})
export class PersonPhoneDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personPhonePopupService: PersonPhonePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.personPhonePopupService
                .open(PersonPhoneDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
