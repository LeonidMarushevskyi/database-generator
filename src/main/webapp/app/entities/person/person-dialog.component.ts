import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Person } from './person.model';
import { PersonPopupService } from './person-popup.service';
import { PersonService } from './person.service';
import { Application, ApplicationService } from '../application';
import { Household, HouseholdService } from '../household';
import { EducationLevelType, EducationLevelTypeService } from '../education-level-type';
import { GenderType, GenderTypeService } from '../gender-type';
import { RaceType, RaceTypeService } from '../race-type';
import { EthnicityType, EthnicityTypeService } from '../ethnicity-type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-person-dialog',
    templateUrl: './person-dialog.component.html'
})
export class PersonDialogComponent implements OnInit {

    person: Person;
    authorities: any[];
    isSaving: boolean;

    applications: Application[];

    households: Household[];

    educationhighestlevels: EducationLevelType[];

    gendertypes: GenderType[];

    racetypes: RaceType[];

    ethnicitytypes: EthnicityType[];
    dateOfBirthDp: any;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private personService: PersonService,
        private applicationService: ApplicationService,
        private householdService: HouseholdService,
        private educationLevelTypeService: EducationLevelTypeService,
        private genderTypeService: GenderTypeService,
        private raceTypeService: RaceTypeService,
        private ethnicityTypeService: EthnicityTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.applicationService.query()
            .subscribe((res: ResponseWrapper) => { this.applications = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.householdService.query()
            .subscribe((res: ResponseWrapper) => { this.households = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.educationLevelTypeService
            .query({filter: 'person-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.person.educationHighestLevel || !this.person.educationHighestLevel.id) {
                    this.educationhighestlevels = res.json;
                } else {
                    this.educationLevelTypeService
                        .find(this.person.educationHighestLevel.id)
                        .subscribe((subRes: EducationLevelType) => {
                            this.educationhighestlevels = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.genderTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.gendertypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.raceTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.racetypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.ethnicityTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.ethnicitytypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.person.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personService.update(this.person));
        } else {
            this.subscribeToSaveResponse(
                this.personService.create(this.person));
        }
    }

    private subscribeToSaveResponse(result: Observable<Person>) {
        result.subscribe((res: Person) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Person) {
        this.eventManager.broadcast({ name: 'personListModification', content: 'OK'});
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

    trackApplicationById(index: number, item: Application) {
        return item.id;
    }

    trackHouseholdById(index: number, item: Household) {
        return item.id;
    }

    trackEducationLevelTypeById(index: number, item: EducationLevelType) {
        return item.id;
    }

    trackGenderTypeById(index: number, item: GenderType) {
        return item.id;
    }

    trackRaceTypeById(index: number, item: RaceType) {
        return item.id;
    }

    trackEthnicityTypeById(index: number, item: EthnicityType) {
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
    selector: 'jhi-person-popup',
    template: ''
})
export class PersonPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personPopupService: PersonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.personPopupService
                    .open(PersonDialogComponent, params['id']);
            } else {
                this.modalRef = this.personPopupService
                    .open(PersonDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
