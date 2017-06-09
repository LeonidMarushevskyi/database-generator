import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { RelationshipType } from './relationship-type.model';
import { RelationshipTypePopupService } from './relationship-type-popup.service';
import { RelationshipTypeService } from './relationship-type.service';

@Component({
    selector: 'jhi-relationship-type-delete-dialog',
    templateUrl: './relationship-type-delete-dialog.component.html'
})
export class RelationshipTypeDeleteDialogComponent {

    relationshipType: RelationshipType;

    constructor(
        private relationshipTypeService: RelationshipTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.relationshipTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'relationshipTypeListModification',
                content: 'Deleted an relationshipType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-relationship-type-delete-popup',
    template: ''
})
export class RelationshipTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private relationshipTypePopupService: RelationshipTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.relationshipTypePopupService
                .open(RelationshipTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
