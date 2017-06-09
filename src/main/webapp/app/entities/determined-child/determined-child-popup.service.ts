import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DeterminedChild } from './determined-child.model';
import { DeterminedChildService } from './determined-child.service';
@Injectable()
export class DeterminedChildPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private determinedChildService: DeterminedChildService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.determinedChildService.find(id).subscribe((determinedChild) => {
                if (determinedChild.dateOfPlacement) {
                    determinedChild.dateOfPlacement = {
                        year: determinedChild.dateOfPlacement.getFullYear(),
                        month: determinedChild.dateOfPlacement.getMonth() + 1,
                        day: determinedChild.dateOfPlacement.getDate()
                    };
                }
                this.determinedChildModalRef(component, determinedChild);
            });
        } else {
            return this.determinedChildModalRef(component, new DeterminedChild());
        }
    }

    determinedChildModalRef(component: Component, determinedChild: DeterminedChild): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.determinedChild = determinedChild;
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
