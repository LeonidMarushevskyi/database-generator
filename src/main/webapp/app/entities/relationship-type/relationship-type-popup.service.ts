import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { RelationshipType } from './relationship-type.model';
import { RelationshipTypeService } from './relationship-type.service';
@Injectable()
export class RelationshipTypePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private relationshipTypeService: RelationshipTypeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.relationshipTypeService.find(id).subscribe((relationshipType) => {
                this.relationshipTypeModalRef(component, relationshipType);
            });
        } else {
            return this.relationshipTypeModalRef(component, new RelationshipType());
        }
    }

    relationshipTypeModalRef(component: Component, relationshipType: RelationshipType): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.relationshipType = relationshipType;
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
