import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { RaceType } from './race-type.model';
import { RaceTypePopupService } from './race-type-popup.service';
import { RaceTypeService } from './race-type.service';

@Component({
    selector: 'jhi-race-type-dialog',
    templateUrl: './race-type-dialog.component.html'
})
export class RaceTypeDialogComponent implements OnInit {

    raceType: RaceType;
    authorities: any[];
    isSaving: boolean;
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private raceTypeService: RaceTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.raceType.id !== undefined) {
            this.raceTypeService.update(this.raceType)
                .subscribe((res: RaceType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.raceTypeService.create(this.raceType)
                .subscribe((res: RaceType) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: RaceType) {
        this.eventManager.broadcast({ name: 'raceTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError (error) {
        this.isSaving = false;
        this.onError(error);
    }

    private onError (error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-race-type-popup',
    template: ''
})
export class RaceTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private raceTypePopupService: RaceTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.raceTypePopupService
                    .open(RaceTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.raceTypePopupService
                    .open(RaceTypeDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
