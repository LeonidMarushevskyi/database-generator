import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { A } from './a.model';
import { APopupService } from './a-popup.service';
import { AService } from './a.service';

@Component({
    selector: 'jhi-a-delete-dialog',
    templateUrl: './a-delete-dialog.component.html'
})
export class ADeleteDialogComponent {

    a: A;

    constructor(
        private aService: AService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear () {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete (id: number) {
        this.aService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'aListModification',
                content: 'Deleted an a'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-a-delete-popup',
    template: ''
})
export class ADeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor (
        private route: ActivatedRoute,
        private aPopupService: APopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe(params => {
            this.modalRef = this.aPopupService
                .open(ADeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
