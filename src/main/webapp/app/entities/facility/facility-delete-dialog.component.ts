import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Facility } from './facility.model';
import { FacilityPopupService } from './facility-popup.service';
import { FacilityService } from './facility.service';

@Component({
    selector: 'jhi-facility-delete-dialog',
    templateUrl: './facility-delete-dialog.component.html'
})
export class FacilityDeleteDialogComponent {

    facility: Facility;

    constructor(
        private facilityService: FacilityService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.facilityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'facilityListModification',
                content: 'Deleted an facility'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-facility-delete-popup',
    template: ''
})
export class FacilityDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private facilityPopupService: FacilityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.facilityPopupService
                .open(FacilityDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
