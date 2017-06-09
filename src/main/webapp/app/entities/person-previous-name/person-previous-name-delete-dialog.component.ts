import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { PersonPreviousName } from './person-previous-name.model';
import { PersonPreviousNamePopupService } from './person-previous-name-popup.service';
import { PersonPreviousNameService } from './person-previous-name.service';

@Component({
    selector: 'jhi-person-previous-name-delete-dialog',
    templateUrl: './person-previous-name-delete-dialog.component.html'
})
export class PersonPreviousNameDeleteDialogComponent {

    personPreviousName: PersonPreviousName;

    constructor(
        private personPreviousNameService: PersonPreviousNameService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personPreviousNameService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'personPreviousNameListModification',
                content: 'Deleted an personPreviousName'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-person-previous-name-delete-popup',
    template: ''
})
export class PersonPreviousNameDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personPreviousNamePopupService: PersonPreviousNamePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.personPreviousNamePopupService
                .open(PersonPreviousNameDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
