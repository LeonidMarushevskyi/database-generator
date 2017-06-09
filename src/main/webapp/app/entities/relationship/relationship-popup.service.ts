import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Relationship } from './relationship.model';
import { RelationshipService } from './relationship.service';
@Injectable()
export class RelationshipPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private relationshipService: RelationshipService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.relationshipService.find(id).subscribe((relationship) => {
                relationship.createDateTime = this.datePipe
                    .transform(relationship.createDateTime, 'yyyy-MM-ddThh:mm');
                relationship.updateDateTime = this.datePipe
                    .transform(relationship.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.relationshipModalRef(component, relationship);
            });
        } else {
            return this.relationshipModalRef(component, new Relationship());
        }
    }

    relationshipModalRef(component: Component, relationship: Relationship): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.relationship = relationship;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
