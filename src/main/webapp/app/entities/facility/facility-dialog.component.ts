import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Facility } from './facility.model';
import { FacilityPopupService } from './facility-popup.service';
import { FacilityService } from './facility.service';
import { FacilityAddress, FacilityAddressService } from '../facility-address';

@Component({
    selector: 'jhi-facility-dialog',
    templateUrl: './facility-dialog.component.html'
})
export class FacilityDialogComponent implements OnInit {

    facility: Facility;
    authorities: any[];
    isSaving: boolean;

    facilityaddresses: FacilityAddress[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private facilityService: FacilityService,
        private facilityAddressService: FacilityAddressService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.facilityAddressService.query().subscribe(
            (res: Response) => { this.facilityaddresses = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.facility.id !== undefined) {
            this.facilityService.update(this.facility)
                .subscribe((res: Facility) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.facilityService.create(this.facility)
                .subscribe((res: Facility) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Facility) {
        this.eventManager.broadcast({ name: 'facilityListModification', content: 'OK'});
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

    trackFacilityAddressById(index: number, item: FacilityAddress) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-facility-popup',
    template: ''
})
export class FacilityPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private facilityPopupService: FacilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.facilityPopupService
                    .open(FacilityDialogComponent, params['id']);
            } else {
                this.modalRef = this.facilityPopupService
                    .open(FacilityDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
