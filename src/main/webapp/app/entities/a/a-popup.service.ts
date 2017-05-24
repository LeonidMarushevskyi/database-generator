import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { A } from './a.model';
import { AService } from './a.service';
@Injectable()
export class APopupService {
    private isOpen = false;
    constructor (
        private modalService: NgbModal,
        private router: Router,
        private aService: AService

    ) {}

    open (component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.aService.find(id).subscribe(a => {
                this.aModalRef(component, a);
            });
        } else {
            return this.aModalRef(component, new A());
        }
    }

    aModalRef(component: Component, a: A): NgbModalRef {
        let modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.a = a;
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
