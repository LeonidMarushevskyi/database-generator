import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { CountyType } from './county-type.model';
import { CountyTypePopupService } from './county-type-popup.service';
import { CountyTypeService } from './county-type.service';
import { StateType, StateTypeService } from '../state-type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-county-type-dialog',
    templateUrl: './county-type-dialog.component.html'
})
export class CountyTypeDialogComponent implements OnInit {

    countyType: CountyType;
    authorities: any[];
    isSaving: boolean;

    statetypes: StateType[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private countyTypeService: CountyTypeService,
        private stateTypeService: StateTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.stateTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.statetypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.countyType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.countyTypeService.update(this.countyType));
        } else {
            this.subscribeToSaveResponse(
                this.countyTypeService.create(this.countyType));
        }
    }

    private subscribeToSaveResponse(result: Observable<CountyType>) {
        result.subscribe((res: CountyType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CountyType) {
        this.eventManager.broadcast({ name: 'countyTypeListModification', content: 'OK'});
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

    trackStateTypeById(index: number, item: StateType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-county-type-popup',
    template: ''
})
export class CountyTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private countyTypePopupService: CountyTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.countyTypePopupService
                    .open(CountyTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.countyTypePopupService
                    .open(CountyTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
