import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { PersonRace } from './person-race.model';
import { PersonRacePopupService } from './person-race-popup.service';
import { PersonRaceService } from './person-race.service';
import { Person, PersonService } from '../person';
import { RaceType, RaceTypeService } from '../race-type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-person-race-dialog',
    templateUrl: './person-race-dialog.component.html'
})
export class PersonRaceDialogComponent implements OnInit {

    personRace: PersonRace;
    authorities: any[];
    isSaving: boolean;

    people: Person[];

    racetypes: RaceType[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private personRaceService: PersonRaceService,
        private personService: PersonService,
        private raceTypeService: RaceTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.raceTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.racetypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.personRace.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personRaceService.update(this.personRace));
        } else {
            this.subscribeToSaveResponse(
                this.personRaceService.create(this.personRace));
        }
    }

    private subscribeToSaveResponse(result: Observable<PersonRace>) {
        result.subscribe((res: PersonRace) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: PersonRace) {
        this.eventManager.broadcast({ name: 'personRaceListModification', content: 'OK'});
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

    trackRaceTypeById(index: number, item: RaceType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-person-race-popup',
    template: ''
})
export class PersonRacePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personRacePopupService: PersonRacePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.personRacePopupService
                    .open(PersonRaceDialogComponent, params['id']);
            } else {
                this.modalRef = this.personRacePopupService
                    .open(PersonRaceDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
