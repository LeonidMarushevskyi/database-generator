import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { FacilityPhone } from './facility-phone.model';
import { FacilityPhonePopupService } from './facility-phone-popup.service';
import { FacilityPhoneService } from './facility-phone.service';

@Component({
    selector: 'jhi-facility-phone-delete-dialog',
    templateUrl: './facility-phone-delete-dialog.component.html'
})
export class FacilityPhoneDeleteDialogComponent {

    facilityPhone: FacilityPhone;

    constructor(
        private facilityPhoneService: FacilityPhoneService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.facilityPhoneService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'facilityPhoneListModification',
                content: 'Deleted an facilityPhone'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-facility-phone-delete-popup',
    template: ''
})
export class FacilityPhoneDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private facilityPhonePopupService: FacilityPhonePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.facilityPhonePopupService
                .open(FacilityPhoneDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
