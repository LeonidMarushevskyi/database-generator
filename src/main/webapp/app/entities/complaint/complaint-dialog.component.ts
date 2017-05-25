import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Complaint } from './complaint.model';
import { ComplaintPopupService } from './complaint-popup.service';
import { ComplaintService } from './complaint.service';
import { Facility, FacilityService } from '../facility';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-complaint-dialog',
    templateUrl: './complaint-dialog.component.html'
})
export class ComplaintDialogComponent implements OnInit {

    complaint: Complaint;
    authorities: any[];
    isSaving: boolean;

    facilities: Facility[];
    complaintDateDp: any;
    approvalDateDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private complaintService: ComplaintService,
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
        if (this.complaint.id !== undefined) {
            this.subscribeToSaveResponse(
                this.complaintService.update(this.complaint));
        } else {
            this.subscribeToSaveResponse(
                this.complaintService.create(this.complaint));
        }
    }

    private subscribeToSaveResponse(result: Observable<Complaint>) {
        result.subscribe((res: Complaint) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Complaint) {
        this.eventManager.broadcast({ name: 'complaintListModification', content: 'OK'});
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
    selector: 'jhi-complaint-popup',
    template: ''
})
export class ComplaintPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private complaintPopupService: ComplaintPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.complaintPopupService
                    .open(ComplaintDialogComponent, params['id']);
            } else {
                this.modalRef = this.complaintPopupService
                    .open(ComplaintDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
