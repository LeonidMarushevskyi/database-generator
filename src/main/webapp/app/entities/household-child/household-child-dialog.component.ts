import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { HouseholdChild } from './household-child.model';
import { HouseholdChildPopupService } from './household-child-popup.service';
import { HouseholdChildService } from './household-child.service';
import { Person, PersonService } from '../person';
import { Household, HouseholdService } from '../household';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-household-child-dialog',
    templateUrl: './household-child-dialog.component.html'
})
export class HouseholdChildDialogComponent implements OnInit {

    householdChild: HouseholdChild;
    authorities: any[];
    isSaving: boolean;

    people: Person[];

    households: Household[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private householdChildService: HouseholdChildService,
        private personService: PersonService,
        private householdService: HouseholdService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personService
            .query({filter: 'householdchild-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.householdChild.person || !this.householdChild.person.id) {
                    this.people = res.json;
                } else {
                    this.personService
                        .find(this.householdChild.person.id)
                        .subscribe((subRes: Person) => {
                            this.people = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.householdService.query()
            .subscribe((res: ResponseWrapper) => { this.households = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.householdChild.id !== undefined) {
            this.subscribeToSaveResponse(
                this.householdChildService.update(this.householdChild));
        } else {
            this.subscribeToSaveResponse(
                this.householdChildService.create(this.householdChild));
        }
    }

    private subscribeToSaveResponse(result: Observable<HouseholdChild>) {
        result.subscribe((res: HouseholdChild) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: HouseholdChild) {
        this.eventManager.broadcast({ name: 'householdChildListModification', content: 'OK'});
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

    trackPersonById(index: number, item: Person) {
        return item.id;
    }

    trackHouseholdById(index: number, item: Household) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-household-child-popup',
    template: ''
})
export class HouseholdChildPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private householdChildPopupService: HouseholdChildPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.householdChildPopupService
                    .open(HouseholdChildDialogComponent, params['id']);
            } else {
                this.modalRef = this.householdChildPopupService
                    .open(HouseholdChildDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
