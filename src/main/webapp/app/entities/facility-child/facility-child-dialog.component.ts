import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { FacilityChild } from './facility-child.model';
import { FacilityChildPopupService } from './facility-child-popup.service';
import { FacilityChildService } from './facility-child.service';
import { Facility, FacilityService } from '../facility';
import { Person, PersonService } from '../person';

@Component({
    selector: 'jhi-facility-child-dialog',
    templateUrl: './facility-child-dialog.component.html'
})
export class FacilityChildDialogComponent implements OnInit {

    facilityChild: FacilityChild;
    authorities: any[];
    isSaving: boolean;

    facilities: Facility[];

    people: Person[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private facilityChildService: FacilityChildService,
        private facilityService: FacilityService,
        private personService: PersonService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.facilityService.query().subscribe(
            (res: Response) => { this.facilities = res.json(); }, (res: Response) => this.onError(res.json()));
        this.personService.query({filter: 'facilitychild-is-null'}).subscribe((res: Response) => {
            if (!this.facilityChild.personId) {
                this.people = res.json();
            } else {
                this.personService.find(this.facilityChild.personId).subscribe((subRes: Person) => {
                    this.people = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.facilityChild.id !== undefined) {
            this.facilityChildService.update(this.facilityChild)
                .subscribe((res: FacilityChild) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.facilityChildService.create(this.facilityChild)
                .subscribe((res: FacilityChild) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: FacilityChild) {
        this.eventManager.broadcast({ name: 'facilityChildListModification', content: 'OK'});
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

    trackFacilityById(index: number, item: Facility) {
        return item.id;
    }

    trackPersonById(index: number, item: Person) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-facility-child-popup',
    template: ''
})
export class FacilityChildPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private facilityChildPopupService: FacilityChildPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            if ( params['id'] ) {
                this.modalRef = this.facilityChildPopupService
                    .open(FacilityChildDialogComponent, params['id']);
            } else {
                this.modalRef = this.facilityChildPopupService
                    .open(FacilityChildDialogComponent);
            }

        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
