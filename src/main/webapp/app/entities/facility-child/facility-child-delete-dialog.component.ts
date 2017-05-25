import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { FacilityChild } from './facility-child.model';
import { FacilityChildPopupService } from './facility-child-popup.service';
import { FacilityChildService } from './facility-child.service';

@Component({
    selector: 'jhi-facility-child-delete-dialog',
    templateUrl: './facility-child-delete-dialog.component.html'
})
export class FacilityChildDeleteDialogComponent {

    facilityChild: FacilityChild;

    constructor(
        private facilityChildService: FacilityChildService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.facilityChildService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'facilityChildListModification',
                content: 'Deleted an facilityChild'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-facility-child-delete-popup',
    template: ''
})
export class FacilityChildDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private facilityChildPopupService: FacilityChildPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.facilityChildPopupService
                .open(FacilityChildDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
