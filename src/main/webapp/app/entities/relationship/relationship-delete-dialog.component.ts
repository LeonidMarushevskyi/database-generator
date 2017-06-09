import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Relationship } from './relationship.model';
import { RelationshipPopupService } from './relationship-popup.service';
import { RelationshipService } from './relationship.service';

@Component({
    selector: 'jhi-relationship-delete-dialog',
    templateUrl: './relationship-delete-dialog.component.html'
})
export class RelationshipDeleteDialogComponent {

    relationship: Relationship;

    constructor(
        private relationshipService: RelationshipService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.relationshipService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'relationshipListModification',
                content: 'Deleted an relationship'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-relationship-delete-popup',
    template: ''
})
export class RelationshipDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private relationshipPopupService: RelationshipPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.relationshipPopupService
                .open(RelationshipDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
