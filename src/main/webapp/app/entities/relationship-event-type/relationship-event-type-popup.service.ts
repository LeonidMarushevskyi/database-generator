import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RelationshipEventType } from './relationship-event-type.model';
import { RelationshipEventTypeService } from './relationship-event-type.service';
@Injectable()
export class RelationshipEventTypePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private relationshipEventTypeService: RelationshipEventTypeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.relationshipEventTypeService.find(id).subscribe((relationshipEventType) => {
                this.relationshipEventTypeModalRef(component, relationshipEventType);
            });
        } else {
            return this.relationshipEventTypeModalRef(component, new RelationshipEventType());
        }
    }

    relationshipEventTypeModalRef(component: Component, relationshipEventType: RelationshipEventType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.relationshipEventType = relationshipEventType;
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
