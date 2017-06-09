import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { SiblingGroupType } from './sibling-group-type.model';
import { SiblingGroupTypePopupService } from './sibling-group-type-popup.service';
import { SiblingGroupTypeService } from './sibling-group-type.service';

@Component({
    selector: 'jhi-sibling-group-type-delete-dialog',
    templateUrl: './sibling-group-type-delete-dialog.component.html'
})
export class SiblingGroupTypeDeleteDialogComponent {

    siblingGroupType: SiblingGroupType;

    constructor(
        private siblingGroupTypeService: SiblingGroupTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.siblingGroupTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'siblingGroupTypeListModification',
                content: 'Deleted an siblingGroupType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-sibling-group-type-delete-popup',
    template: ''
})
export class SiblingGroupTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private siblingGroupTypePopupService: SiblingGroupTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.siblingGroupTypePopupService
                .open(SiblingGroupTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
