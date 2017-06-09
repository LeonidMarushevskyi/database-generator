import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { DeterminedChild } from './determined-child.model';
import { DeterminedChildPopupService } from './determined-child-popup.service';
import { DeterminedChildService } from './determined-child.service';

@Component({
    selector: 'jhi-determined-child-delete-dialog',
    templateUrl: './determined-child-delete-dialog.component.html'
})
export class DeterminedChildDeleteDialogComponent {

    determinedChild: DeterminedChild;

    constructor(
        private determinedChildService: DeterminedChildService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.determinedChildService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'determinedChildListModification',
                content: 'Deleted an determinedChild'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-determined-child-delete-popup',
    template: ''
})
export class DeterminedChildDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private determinedChildPopupService: DeterminedChildPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.determinedChildPopupService
                .open(DeterminedChildDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
