import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { StateType } from './state-type.model';
import { StateTypePopupService } from './state-type-popup.service';
import { StateTypeService } from './state-type.service';

@Component({
    selector: 'jhi-state-type-dialog',
    templateUrl: './state-type-dialog.component.html'
})
export class StateTypeDialogComponent implements OnInit {

    stateType: StateType;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private stateTypeService: StateTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.stateType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.stateTypeService.update(this.stateType));
        } else {
            this.subscribeToSaveResponse(
                this.stateTypeService.create(this.stateType));
        }
    }

    private subscribeToSaveResponse(result: Observable<StateType>) {
        result.subscribe((res: StateType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: StateType) {
        this.eventManager.broadcast({ name: 'stateTypeListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-state-type-popup',
    template: ''
})
export class StateTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private stateTypePopupService: StateTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.stateTypePopupService
                    .open(StateTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.stateTypePopupService
                    .open(StateTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
