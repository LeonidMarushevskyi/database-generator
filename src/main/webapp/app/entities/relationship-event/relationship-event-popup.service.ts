import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { RelationshipEvent } from './relationship-event.model';
import { RelationshipEventService } from './relationship-event.service';
@Injectable()
export class RelationshipEventPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private relationshipEventService: RelationshipEventService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.relationshipEventService.find(id).subscribe((relationshipEvent) => {
                if (relationshipEvent.eventDate) {
                    relationshipEvent.eventDate = {
                        year: relationshipEvent.eventDate.getFullYear(),
                        month: relationshipEvent.eventDate.getMonth() + 1,
                        day: relationshipEvent.eventDate.getDate()
                    };
                }
                relationshipEvent.createDateTime = this.datePipe
                    .transform(relationshipEvent.createDateTime, 'yyyy-MM-ddThh:mm');
                relationshipEvent.updateDateTime = this.datePipe
                    .transform(relationshipEvent.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.relationshipEventModalRef(component, relationshipEvent);
            });
        } else {
            return this.relationshipEventModalRef(component, new RelationshipEvent());
        }
    }

    relationshipEventModalRef(component: Component, relationshipEvent: RelationshipEvent): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.relationshipEvent = relationshipEvent;
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
