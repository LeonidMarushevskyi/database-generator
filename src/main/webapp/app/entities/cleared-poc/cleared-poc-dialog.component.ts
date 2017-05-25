import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { ClearedPOC } from './cleared-poc.model';
import { ClearedPOCPopupService } from './cleared-poc-popup.service';
import { ClearedPOCService } from './cleared-poc.service';
import { Inspection, InspectionService } from '../inspection';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-cleared-poc-dialog',
    templateUrl: './cleared-poc-dialog.component.html'
})
export class ClearedPOCDialogComponent implements OnInit {

    clearedPOC: ClearedPOC;
    authorities: any[];
    isSaving: boolean;

    inspections: Inspection[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private clearedPOCService: ClearedPOCService,
        private inspectionService: InspectionService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.inspectionService.query()
            .subscribe((res: ResponseWrapper) => { this.inspections = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.clearedPOC.id !== undefined) {
            this.subscribeToSaveResponse(
                this.clearedPOCService.update(this.clearedPOC));
        } else {
            this.subscribeToSaveResponse(
                this.clearedPOCService.create(this.clearedPOC));
        }
    }

    private subscribeToSaveResponse(result: Observable<ClearedPOC>) {
        result.subscribe((res: ClearedPOC) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: ClearedPOC) {
        this.eventManager.broadcast({ name: 'clearedPOCListModification', content: 'OK'});
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

    trackInspectionById(index: number, item: Inspection) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-cleared-poc-popup',
    template: ''
})
export class ClearedPOCPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private clearedPOCPopupService: ClearedPOCPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.clearedPOCPopupService
                    .open(ClearedPOCDialogComponent, params['id']);
            } else {
                this.modalRef = this.clearedPOCPopupService
                    .open(ClearedPOCDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
