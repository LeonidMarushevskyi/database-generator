import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { County } from './county.model';
import { CountyPopupService } from './county-popup.service';
import { CountyService } from './county.service';

@Component({
    selector: 'jhi-county-delete-dialog',
    templateUrl: './county-delete-dialog.component.html'
})
export class CountyDeleteDialogComponent {

    county: County;

    constructor(
        private countyService: CountyService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.countyService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'countyListModification',
                content: 'Deleted an county'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-county-delete-popup',
    template: ''
})
export class CountyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private countyPopupService: CountyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.countyPopupService
                .open(CountyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
