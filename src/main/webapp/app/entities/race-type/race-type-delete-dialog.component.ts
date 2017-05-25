import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { RaceType } from './race-type.model';
import { RaceTypePopupService } from './race-type-popup.service';
import { RaceTypeService } from './race-type.service';

@Component({
    selector: 'jhi-race-type-delete-dialog',
    templateUrl: './race-type-delete-dialog.component.html'
})
export class RaceTypeDeleteDialogComponent {

    raceType: RaceType;

    constructor(
        private raceTypeService: RaceTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.raceTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'raceTypeListModification',
                content: 'Deleted an raceType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-race-type-delete-popup',
    template: ''
})
export class RaceTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private raceTypePopupService: RaceTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.raceTypePopupService
                .open(RaceTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
