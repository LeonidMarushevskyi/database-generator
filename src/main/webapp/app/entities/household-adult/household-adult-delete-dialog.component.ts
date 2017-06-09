import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { HouseholdAdult } from './household-adult.model';
import { HouseholdAdultPopupService } from './household-adult-popup.service';
import { HouseholdAdultService } from './household-adult.service';

@Component({
    selector: 'jhi-household-adult-delete-dialog',
    templateUrl: './household-adult-delete-dialog.component.html'
})
export class HouseholdAdultDeleteDialogComponent {

    householdAdult: HouseholdAdult;

    constructor(
        private householdAdultService: HouseholdAdultService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.householdAdultService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'householdAdultListModification',
                content: 'Deleted an householdAdult'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-household-adult-delete-popup',
    template: ''
})
export class HouseholdAdultDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private householdAdultPopupService: HouseholdAdultPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.householdAdultPopupService
                .open(HouseholdAdultDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
