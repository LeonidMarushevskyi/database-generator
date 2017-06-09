import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Relationship } from './relationship.model';
import { RelationshipPopupService } from './relationship-popup.service';
import { RelationshipService } from './relationship.service';
import { Person, PersonService } from '../person';
import { RelationshipType, RelationshipTypeService } from '../relationship-type';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-relationship-dialog',
    templateUrl: './relationship-dialog.component.html'
})
export class RelationshipDialogComponent implements OnInit {

    relationship: Relationship;
    authorities: any[];
    isSaving: boolean;

    people: Person[];

    relationshiptypes: RelationshipType[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private relationshipService: RelationshipService,
        private personService: PersonService,
        private relationshipTypeService: RelationshipTypeService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.relationshipTypeService.query()
            .subscribe((res: ResponseWrapper) => { this.relationshiptypes = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.relationship.id !== undefined) {
            this.subscribeToSaveResponse(
                this.relationshipService.update(this.relationship));
        } else {
            this.subscribeToSaveResponse(
                this.relationshipService.create(this.relationship));
        }
    }

    private subscribeToSaveResponse(result: Observable<Relationship>) {
        result.subscribe((res: Relationship) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Relationship) {
        this.eventManager.broadcast({ name: 'relationshipListModification', content: 'OK'});
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

    trackRelationshipTypeById(index: number, item: RelationshipType) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-relationship-popup',
    template: ''
})
export class RelationshipPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private relationshipPopupService: RelationshipPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.relationshipPopupService
                    .open(RelationshipDialogComponent, params['id']);
            } else {
                this.modalRef = this.relationshipPopupService
                    .open(RelationshipDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
