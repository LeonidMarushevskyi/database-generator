import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { AssignedWorker } from './assigned-worker.model';
import { AssignedWorkerService } from './assigned-worker.service';
@Injectable()
export class AssignedWorkerPopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private assignedWorkerService: AssignedWorkerService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.assignedWorkerService.find(id).subscribe(assignedWorker => {
                this.assignedWorkerModalRef(component, assignedWorker);
            });
        } else {
            return this.assignedWorkerModalRef(component, new AssignedWorker());
        }
    }

    assignedWorkerModalRef(component: Component, assignedWorker: AssignedWorker): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.assignedWorker = assignedWorker;
        modalRef.result.then(result => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
