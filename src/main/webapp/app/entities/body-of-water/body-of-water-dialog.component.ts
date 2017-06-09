import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { BodyOfWater } from './body-of-water.model';
import { BodyOfWaterPopupService } from './body-of-water-popup.service';
import { BodyOfWaterService } from './body-of-water.service';
import { HouseholdAddress, HouseholdAddressService } from '../household-address';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-body-of-water-dialog',
    templateUrl: './body-of-water-dialog.component.html'
})
export class BodyOfWaterDialogComponent implements OnInit {

    bodyOfWater: BodyOfWater;
    authorities: any[];
    isSaving: boolean;

    householdaddresses: HouseholdAddress[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private bodyOfWaterService: BodyOfWaterService,
        private householdAddressService: HouseholdAddressService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.householdAddressService.query()
            .subscribe((res: ResponseWrapper) => { this.householdaddresses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.bodyOfWater.id !== undefined) {
            this.subscribeToSaveResponse(
                this.bodyOfWaterService.update(this.bodyOfWater));
        } else {
            this.subscribeToSaveResponse(
                this.bodyOfWaterService.create(this.bodyOfWater));
        }
    }

    private subscribeToSaveResponse(result: Observable<BodyOfWater>) {
        result.subscribe((res: BodyOfWater) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: BodyOfWater) {
        this.eventManager.broadcast({ name: 'bodyOfWaterListModification', content: 'OK'});
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

    trackHouseholdAddressById(index: number, item: HouseholdAddress) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-body-of-water-popup',
    template: ''
})
export class BodyOfWaterPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bodyOfWaterPopupService: BodyOfWaterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.bodyOfWaterPopupService
                    .open(BodyOfWaterDialogComponent, params['id']);
            } else {
                this.modalRef = this.bodyOfWaterPopupService
                    .open(BodyOfWaterDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
