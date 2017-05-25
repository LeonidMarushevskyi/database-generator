import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Inspection } from './inspection.model';
import { InspectionPopupService } from './inspection-popup.service';
import { InspectionService } from './inspection.service';
import { Facility, FacilityService } from '../facility';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-inspection-dialog',
    templateUrl: './inspection-dialog.component.html'
})
export class InspectionDialogComponent implements OnInit {

    inspection: Inspection;
    authorities: any[];
    isSaving: boolean;

    facilities: Facility[];
    representativeSignatureDateDp: any;
    form809PrintDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private inspectionService: InspectionService,
        private facilityService: FacilityService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.facilityService.query()
            .subscribe((res: ResponseWrapper) => { this.facilities = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.inspection.id !== undefined) {
            this.subscribeToSaveResponse(
                this.inspectionService.update(this.inspection));
        } else {
            this.subscribeToSaveResponse(
                this.inspectionService.create(this.inspection));
        }
    }

    private subscribeToSaveResponse(result: Observable<Inspection>) {
        result.subscribe((res: Inspection) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Inspection) {
        this.eventManager.broadcast({ name: 'inspectionListModification', content: 'OK'});
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

    trackFacilityById(index: number, item: Facility) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-inspection-popup',
    template: ''
})
export class InspectionPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inspectionPopupService: InspectionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.inspectionPopupService
                    .open(InspectionDialogComponent, params['id']);
            } else {
                this.modalRef = this.inspectionPopupService
                    .open(InspectionDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
