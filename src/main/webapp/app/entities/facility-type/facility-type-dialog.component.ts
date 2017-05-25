import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { FacilityType } from './facility-type.model';
import { FacilityTypePopupService } from './facility-type-popup.service';
import { FacilityTypeService } from './facility-type.service';

@Component({
    selector: 'jhi-facility-type-dialog',
    templateUrl: './facility-type-dialog.component.html'
})
export class FacilityTypeDialogComponent implements OnInit {

    facilityType: FacilityType;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private facilityTypeService: FacilityTypeService,
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
        if (this.facilityType.id !== undefined) {
            this.subscribeToSaveResponse(
                this.facilityTypeService.update(this.facilityType));
        } else {
            this.subscribeToSaveResponse(
                this.facilityTypeService.create(this.facilityType));
        }
    }

    private subscribeToSaveResponse(result: Observable<FacilityType>) {
        result.subscribe((res: FacilityType) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: FacilityType) {
        this.eventManager.broadcast({ name: 'facilityTypeListModification', content: 'OK'});
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
    selector: 'jhi-facility-type-popup',
    template: ''
})
export class FacilityTypePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private facilityTypePopupService: FacilityTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.facilityTypePopupService
                    .open(FacilityTypeDialogComponent, params['id']);
            } else {
                this.modalRef = this.facilityTypePopupService
                    .open(FacilityTypeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
