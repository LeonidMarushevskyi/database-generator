import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService, DataUtils } from 'ng-jhipster';

import { CriminalRecord } from './criminal-record.model';
import { CriminalRecordPopupService } from './criminal-record-popup.service';
import { CriminalRecordService } from './criminal-record.service';
import { StateType, StateTypeService } from '../state-type';
import { HouseholdAdult, HouseholdAdultService } from '../household-adult';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-criminal-record-dialog',
    templateUrl: './criminal-record-dialog.component.html'
})
export class CriminalRecordDialogComponent implements OnInit {

    criminalRecord: CriminalRecord;
    authorities: any[];
    isSaving: boolean;

    states: StateType[];

    householdadults: HouseholdAdult[];
    offenseDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: DataUtils,
        private alertService: AlertService,
        private criminalRecordService: CriminalRecordService,
        private stateTypeService: StateTypeService,
        private householdAdultService: HouseholdAdultService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.stateTypeService
            .query({filter: 'criminalrecord-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.criminalRecord.state || !this.criminalRecord.state.id) {
                    this.states = res.json;
                } else {
                    this.stateTypeService
                        .find(this.criminalRecord.state.id)
                        .subscribe((subRes: StateType) => {
                            this.states = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.householdAdultService.query()
            .subscribe((res: ResponseWrapper) => { this.householdadults = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, criminalRecord, field, isImage) {
        if (event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                criminalRecord[field] = base64Data;
                criminalRecord[`${field}ContentType`] = file.type;
            });
        }
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.criminalRecord.id !== undefined) {
            this.subscribeToSaveResponse(
                this.criminalRecordService.update(this.criminalRecord));
        } else {
            this.subscribeToSaveResponse(
                this.criminalRecordService.create(this.criminalRecord));
        }
    }

    private subscribeToSaveResponse(result: Observable<CriminalRecord>) {
        result.subscribe((res: CriminalRecord) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: CriminalRecord) {
        this.eventManager.broadcast({ name: 'criminalRecordListModification', content: 'OK'});
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

    trackHouseholdAdultById(index: number, item: HouseholdAdult) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-criminal-record-popup',
    template: ''
})
export class CriminalRecordPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private criminalRecordPopupService: CriminalRecordPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.criminalRecordPopupService
                    .open(CriminalRecordDialogComponent, params['id']);
            } else {
                this.modalRef = this.criminalRecordPopupService
                    .open(CriminalRecordDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
