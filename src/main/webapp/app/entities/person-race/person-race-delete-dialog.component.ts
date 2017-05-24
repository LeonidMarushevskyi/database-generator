import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { PersonRace } from './person-race.model';
import { PersonRacePopupService } from './person-race-popup.service';
import { PersonRaceService } from './person-race.service';

@Component({
    selector: 'jhi-person-race-delete-dialog',
    templateUrl: './person-race-delete-dialog.component.html'
})
export class PersonRaceDeleteDialogComponent {

    personRace: PersonRace;

    constructor(
        private personRaceService: PersonRaceService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.personRaceService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'personRaceListModification',
                content: 'Deleted an personRace'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-person-race-delete-popup',
    template: ''
})
export class PersonRaceDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private personRacePopupService: PersonRacePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.personRacePopupService
                .open(PersonRaceDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
