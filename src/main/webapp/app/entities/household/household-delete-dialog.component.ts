import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Household } from './household.model';
import { HouseholdPopupService } from './household-popup.service';
import { HouseholdService } from './household.service';

@Component({
    selector: 'jhi-household-delete-dialog',
    templateUrl: './household-delete-dialog.component.html'
})
export class HouseholdDeleteDialogComponent {

    household: Household;

    constructor(
        private householdService: HouseholdService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.householdService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'householdListModification',
                content: 'Deleted an household'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-household-delete-popup',
    template: ''
})
export class HouseholdDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private householdPopupService: HouseholdPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.householdPopupService
                .open(HouseholdDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
