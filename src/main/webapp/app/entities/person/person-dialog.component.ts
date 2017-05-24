import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Person } from './person.model';
import { PersonPopupService } from './person-popup.service';
import { PersonService } from './person.service';
import { PersonEthnicity, PersonEthnicityService } from '../person-ethnicity';
import { PersonPhone, PersonPhoneService } from '../person-phone';
import { PersonAddress, PersonAddressService } from '../person-address';
import { PersonLanguage, PersonLanguageService } from '../person-language';
import { PersonRace, PersonRaceService } from '../person-race';

@Component({
    selector: 'jhi-person-dialog',
    templateUrl: './person-dialog.component.html'
})
export class PersonDialogComponent implements OnInit {

    person: Person;
    authorities: any[];
    isSaving: boolean;

    ethnicities: PersonEthnicity[];

    personphones: PersonPhone[];

    personaddresses: PersonAddress[];

    personlanguages: PersonLanguage[];

    personraces: PersonRace[];
    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private personService: PersonService,
        private personEthnicityService: PersonEthnicityService,
        private personPhoneService: PersonPhoneService,
        private personAddressService: PersonAddressService,
        private personLanguageService: PersonLanguageService,
        private personRaceService: PersonRaceService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personEthnicityService.query({filter: 'person-is-null'}).subscribe((res: Response) => {
            if (!this.person.ethnicityId) {
                this.ethnicities = res.json();
            } else {
                this.personEthnicityService.find(this.person.ethnicityId).subscribe((subRes: PersonEthnicity) => {
                    this.ethnicities = [subRes].concat(res.json());
                }, (subRes: Response) => this.onError(subRes.json()));
            }
        }, (res: Response) => this.onError(res.json()));
        this.personPhoneService.query().subscribe(
            (res: Response) => { this.personphones = res.json(); }, (res: Response) => this.onError(res.json()));
        this.personAddressService.query().subscribe(
            (res: Response) => { this.personaddresses = res.json(); }, (res: Response) => this.onError(res.json()));
        this.personLanguageService.query().subscribe(
            (res: Response) => { this.personlanguages = res.json(); }, (res: Response) => this.onError(res.json()));
        this.personRaceService.query().subscribe(
            (res: Response) => { this.personraces = res.json(); }, (res: Response) => this.onError(res.json()));
    }
    clear () {
        this.activeModal.dismiss('cancel');
    }

    save () {
        this.isSaving = true;
        if (this.person.id !== undefined) {
            this.personService.update(this.person)
                .subscribe((res: Person) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        } else {
            this.personService.create(this.person)
                .subscribe((res: Person) =>
                    this.onSaveSuccess(res), (res: Response) => this.onSaveError(res.json()));
        }
    }

    private onSaveSuccess (result: Person) {
        this.eventManager.broadcast({ name: 'personListModification', content: 'OK'});
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

    trackPersonEthnicityById(index: number, item: PersonEthnicity) {
        return item.id;
    }

    trackPersonPhoneById(index: number, item: PersonPhone) {
        return item.id;
    }

    trackPersonAddressById(index: number, item: PersonAddress) {
        return item.id;
    }

    trackPersonLanguageById(index: number, item: PersonLanguage) {
        return item.id;
    }

    trackPersonRaceById(index: number, item: PersonRace) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-person-popup',
    template: ''
})
export class PersonPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private personPopupService: PersonPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
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
