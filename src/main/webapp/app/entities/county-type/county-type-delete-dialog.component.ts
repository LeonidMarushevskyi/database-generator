import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { CountyType } from './county-type.model';
import { CountyTypePopupService } from './county-type-popup.service';
import { CountyTypeService } from './county-type.service';

@Component({
    selector: 'jhi-county-type-delete-dialog',
    templateUrl: './county-type-delete-dialog.component.html'
})
export class CountyTypeDeleteDialogComponent {

    countyType: CountyType;

    constructor(
        private countyTypeService: CountyTypeService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.countyTypeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'countyTypeListModification',
                content: 'Deleted an countyType'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-county-type-delete-popup',
    template: ''
})
export class CountyTypeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private countyTypePopupService: CountyTypePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.countyTypePopupService
                .open(CountyTypeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
