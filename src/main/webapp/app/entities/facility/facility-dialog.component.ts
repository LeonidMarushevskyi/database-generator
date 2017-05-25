import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Facility } from './facility.model';
import { FacilityPopupService } from './facility-popup.service';
import { FacilityService } from './facility.service';
import { AssignedWorker, AssignedWorkerService } from '../assigned-worker';
import { DistrictOffice, DistrictOfficeService } from '../district-office';
import { FacilityType, FacilityTypeService } from '../facility-type';
import { FacilityStatus, FacilityStatusService } from '../facility-status';
import { County, CountyService } from '../county';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-facility-dialog',
    templateUrl: './facility-dialog.component.html'
})
export class FacilityDialogComponent implements OnInit {

    facility: Facility;
    authorities: any[];
    isSaving: boolean;

    assignedworkers: AssignedWorker[];

    districtoffices: DistrictOffice[];

    facilitytypes: FacilityType[];

    facilitystatuses: FacilityStatus[];

    counties: County[];
    licenseEffectiveDateDp: any;
    originalApplicationRecievedDateDp: any;
    lastVisitDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private facilityService: FacilityService,
        private assignedWorkerService: AssignedWorkerService,
        private districtOfficeService: DistrictOfficeService,
        private facilityTypeService: FacilityTypeService,
        private facilityStatusService: FacilityStatusService,
        private countyService: CountyService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.assignedWorkerService.query()
            .subscribe((res: ResponseWrapper) => { this.assignedworkers = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.districtOfficeService.query()
            .subscribe((res: ResponseWrapper) => { this.districtoffices = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.facilityTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.facilitytypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.facilityStatusService.query()
            .subscribe((res: ResponseWrapper) => { this.facilitystatuses = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.countyService.query()
            .subscribe((res: ResponseWrapper) => { this.counties = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.facility.id !== undefined) {
            this.subscribeToSaveResponse(
                this.facilityService.update(this.facility));
        } else {
            this.subscribeToSaveResponse(
                this.facilityService.create(this.facility));
        }
    }

    private subscribeToSaveResponse(result: Observable<Facility>) {
        result.subscribe((res: Facility) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Facility) {
        this.eventManager.broadcast({ name: 'facilityListModification', content: 'OK'});
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

    trackAssignedWorkerById(index: number, item: AssignedWorker) {
        return item.id;
    }

    trackDistrictOfficeById(index: number, item: DistrictOffice) {
        return item.id;
    }

    trackFacilityTypeById(index: number, item: FacilityType) {
        return item.id;
    }

    trackFacilityStatusById(index: number, item: FacilityStatus) {
        return item.id;
    }

    trackCountyById(index: number, item: County) {
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

    constructor(
        private route: ActivatedRoute,
        private facilityPopupService: FacilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
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
