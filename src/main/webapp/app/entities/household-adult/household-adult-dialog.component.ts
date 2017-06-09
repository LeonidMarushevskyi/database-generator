import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { HouseholdAdult } from './household-adult.model';
import { HouseholdAdultPopupService } from './household-adult-popup.service';
import { HouseholdAdultService } from './household-adult.service';
import { Person, PersonService } from '../person';
import { Household, HouseholdService } from '../household';
import { StateType, StateTypeService } from '../state-type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-household-adult-dialog',
    templateUrl: './household-adult-dialog.component.html'
})
export class HouseholdAdultDialogComponent implements OnInit {

    householdAdult: HouseholdAdult;
    authorities: any[];
    isSaving: boolean;

    people: Person[];

    households: Household[];

    statetypes: StateType[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private householdAdultService: HouseholdAdultService,
        private personService: PersonService,
        private householdService: HouseholdService,
        private stateTypeService: StateTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personService
            .query({filter: 'householdadult-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.householdAdult.person || !this.householdAdult.person.id) {
                    this.people = res.json;
                } else {
                    this.personService
                        .find(this.householdAdult.person.id)
                        .subscribe((subRes: Person) => {
                            this.people = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.householdService.query()
            .subscribe((res: ResponseWrapper) => { this.households = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.stateTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.statetypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.householdAdult.id !== undefined) {
            this.subscribeToSaveResponse(
                this.householdAdultService.update(this.householdAdult));
        } else {
            this.subscribeToSaveResponse(
                this.householdAdultService.create(this.householdAdult));
        }
    }

    private subscribeToSaveResponse(result: Observable<HouseholdAdult>) {
        result.subscribe((res: HouseholdAdult) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: HouseholdAdult) {
        this.eventManager.broadcast({ name: 'householdAdultListModification', content: 'OK'});
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

    trackStateTypeById(index: number, item: StateType) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-household-adult-popup',
    template: ''
})
export class HouseholdAdultPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private householdAdultPopupService: HouseholdAdultPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.householdAdultPopupService
                    .open(HouseholdAdultDialogComponent, params['id']);
            } else {
                this.modalRef = this.householdAdultPopupService
                    .open(HouseholdAdultDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
