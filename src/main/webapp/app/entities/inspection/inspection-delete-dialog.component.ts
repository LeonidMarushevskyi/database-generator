import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Inspection } from './inspection.model';
import { InspectionPopupService } from './inspection-popup.service';
import { InspectionService } from './inspection.service';

@Component({
    selector: 'jhi-inspection-delete-dialog',
    templateUrl: './inspection-delete-dialog.component.html'
})
export class InspectionDeleteDialogComponent {

    inspection: Inspection;

    constructor(
        private inspectionService: InspectionService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.inspectionService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'inspectionListModification',
                content: 'Deleted an inspection'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-inspection-delete-popup',
    template: ''
})
export class InspectionDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private inspectionPopupService: InspectionPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.inspectionPopupService
                .open(InspectionDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
