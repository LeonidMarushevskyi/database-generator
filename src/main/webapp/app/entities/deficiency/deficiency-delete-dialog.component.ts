import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Deficiency } from './deficiency.model';
import { DeficiencyPopupService } from './deficiency-popup.service';
import { DeficiencyService } from './deficiency.service';

@Component({
    selector: 'jhi-deficiency-delete-dialog',
    templateUrl: './deficiency-delete-dialog.component.html'
})
export class DeficiencyDeleteDialogComponent {

    deficiency: Deficiency;

    constructor(
        private deficiencyService: DeficiencyService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.deficiencyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'deficiencyListModification',
                content: 'Deleted an deficiency'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-deficiency-delete-popup',
    template: ''
})
export class DeficiencyDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private deficiencyPopupService: DeficiencyPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.deficiencyPopupService
                .open(DeficiencyDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
