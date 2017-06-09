import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { RelationshipEvent } from './relationship-event.model';
import { RelationshipEventPopupService } from './relationship-event-popup.service';
import { RelationshipEventService } from './relationship-event.service';

@Component({
    selector: 'jhi-relationship-event-delete-dialog',
    templateUrl: './relationship-event-delete-dialog.component.html'
})
export class RelationshipEventDeleteDialogComponent {

    relationshipEvent: RelationshipEvent;

    constructor(
        private relationshipEventService: RelationshipEventService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.relationshipEventService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'relationshipEventListModification',
                content: 'Deleted an relationshipEvent'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-relationship-event-delete-popup',
    template: ''
})
export class RelationshipEventDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private relationshipEventPopupService: RelationshipEventPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.relationshipEventPopupService
                .open(RelationshipEventDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
