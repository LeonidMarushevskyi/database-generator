import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { AgeGroupType } from './age-group-type.model';
import { AgeGroupTypePopupService } from './age-group-type-popup.service';
import { AgeGroupTypeService } from './age-group-type.service';

@Component({
    selector: 'jhi-age-group-type-delete-dialog',
    templateUrl: './age-group-type-delete-dialog.component.html'
})
export class AgeGroupTypeDeleteDialogComponent {

    ageGroupType: AgeGroupType;

    constructor(
        private ageGroupTypeService: AgeGroupTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.ageGroupTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'ageGroupTypeListModification',
                content: 'Deleted an ageGroupType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-age-group-type-delete-popup',
    template: ''
})
export class AgeGroupTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private ageGroupTypePopupService: AgeGroupTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.ageGroupTypePopupService
                .open(AgeGroupTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
