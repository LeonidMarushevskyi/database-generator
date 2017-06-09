import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { HouseholdChild } from './household-child.model';
import { HouseholdChildPopupService } from './household-child-popup.service';
import { HouseholdChildService } from './household-child.service';

@Component({
    selector: 'jhi-household-child-delete-dialog',
    templateUrl: './household-child-delete-dialog.component.html'
})
export class HouseholdChildDeleteDialogComponent {

    householdChild: HouseholdChild;

    constructor(
        private householdChildService: HouseholdChildService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.householdChildService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'householdChildListModification',
                content: 'Deleted an householdChild'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-household-child-delete-popup',
    template: ''
})
export class HouseholdChildDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private householdChildPopupService: HouseholdChildPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.householdChildPopupService
                .open(HouseholdChildDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
