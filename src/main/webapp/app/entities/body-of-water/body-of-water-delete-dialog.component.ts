import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { BodyOfWater } from './body-of-water.model';
import { BodyOfWaterPopupService } from './body-of-water-popup.service';
import { BodyOfWaterService } from './body-of-water.service';

@Component({
    selector: 'jhi-body-of-water-delete-dialog',
    templateUrl: './body-of-water-delete-dialog.component.html'
})
export class BodyOfWaterDeleteDialogComponent {

    bodyOfWater: BodyOfWater;

    constructor(
        private bodyOfWaterService: BodyOfWaterService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bodyOfWaterService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bodyOfWaterListModification',
                content: 'Deleted an bodyOfWater'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-body-of-water-delete-popup',
    template: ''
})
export class BodyOfWaterDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bodyOfWaterPopupService: BodyOfWaterPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bodyOfWaterPopupService
                .open(BodyOfWaterDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
