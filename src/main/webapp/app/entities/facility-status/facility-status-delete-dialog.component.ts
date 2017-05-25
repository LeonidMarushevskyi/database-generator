import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { FacilityStatus } from './facility-status.model';
import { FacilityStatusPopupService } from './facility-status-popup.service';
import { FacilityStatusService } from './facility-status.service';

@Component({
    selector: 'jhi-facility-status-delete-dialog',
    templateUrl: './facility-status-delete-dialog.component.html'
})
export class FacilityStatusDeleteDialogComponent {

    facilityStatus: FacilityStatus;

    constructor(
        private facilityStatusService: FacilityStatusService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.facilityStatusService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'facilityStatusListModification',
                content: 'Deleted an facilityStatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-facility-status-delete-popup',
    template: ''
})
export class FacilityStatusDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private facilityStatusPopupService: FacilityStatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.facilityStatusPopupService
                .open(FacilityStatusDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
