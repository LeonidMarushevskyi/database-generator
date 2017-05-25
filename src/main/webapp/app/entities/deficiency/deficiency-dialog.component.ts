import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Deficiency } from './deficiency.model';
import { DeficiencyPopupService } from './deficiency-popup.service';
import { DeficiencyService } from './deficiency.service';
import { Inspection, InspectionService } from '../inspection';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-deficiency-dialog',
    templateUrl: './deficiency-dialog.component.html'
})
export class DeficiencyDialogComponent implements OnInit {

    deficiency: Deficiency;
    authorities: any[];
    isSaving: boolean;

    inspections: Inspection[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private deficiencyService: DeficiencyService,
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
        if (this.deficiency.id !== undefined) {
            this.subscribeToSaveResponse(
                this.deficiencyService.update(this.deficiency));
        } else {
            this.subscribeToSaveResponse(
                this.deficiencyService.create(this.deficiency));
        }
    }

    private subscribeToSaveResponse(result: Observable<Deficiency>) {
        result.subscribe((res: Deficiency) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Deficiency) {
        this.eventManager.broadcast({ name: 'deficiencyListModification', content: 'OK'});
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
    selector: 'jhi-deficiency-popup',
    template: ''
})
export class DeficiencyPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private deficiencyPopupService: DeficiencyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.deficiencyPopupService
                    .open(DeficiencyDialogComponent, params['id']);
            } else {
                this.modalRef = this.deficiencyPopupService
                    .open(DeficiencyDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
