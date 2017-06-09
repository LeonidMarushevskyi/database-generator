import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { RelationshipEventType } from './relationship-event-type.model';
import { RelationshipEventTypePopupService } from './relationship-event-type-popup.service';
import { RelationshipEventTypeService } from './relationship-event-type.service';

@Component({
    selector: 'jhi-relationship-event-type-delete-dialog',
    templateUrl: './relationship-event-type-delete-dialog.component.html'
})
export class RelationshipEventTypeDeleteDialogComponent {

    relationshipEventType: RelationshipEventType;

    constructor(
        private relationshipEventTypeService: RelationshipEventTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.relationshipEventTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'relationshipEventTypeListModification',
                content: 'Deleted an relationshipEventType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-relationship-event-type-delete-popup',
    template: ''
})
export class RelationshipEventTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private relationshipEventTypePopupService: RelationshipEventTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.relationshipEventTypePopupService
                .open(RelationshipEventTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
