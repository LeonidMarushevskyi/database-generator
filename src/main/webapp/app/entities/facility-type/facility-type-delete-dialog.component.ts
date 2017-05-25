import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { FacilityType } from './facility-type.model';
import { FacilityTypePopupService } from './facility-type-popup.service';
import { FacilityTypeService } from './facility-type.service';

@Component({
    selector: 'jhi-facility-type-delete-dialog',
    templateUrl: './facility-type-delete-dialog.component.html'
})
export class FacilityTypeDeleteDialogComponent {

    facilityType: FacilityType;

    constructor(
        private facilityTypeService: FacilityTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.facilityTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'facilityTypeListModification',
                content: 'Deleted an facilityType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-facility-type-delete-popup',
    template: ''
})
export class FacilityTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private facilityTypePopupService: FacilityTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.facilityTypePopupService
                .open(FacilityTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
