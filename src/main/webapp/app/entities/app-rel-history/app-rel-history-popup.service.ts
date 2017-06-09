import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { AppRelHistory } from './app-rel-history.model';
import { AppRelHistoryService } from './app-rel-history.service';
@Injectable()
export class AppRelHistoryPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private appRelHistoryService: AppRelHistoryService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.appRelHistoryService.find(id).subscribe((appRelHistory) => {
                appRelHistory.createDateTime = this.datePipe
                    .transform(appRelHistory.createDateTime, 'yyyy-MM-ddThh:mm');
                appRelHistory.updateDateTime = this.datePipe
                    .transform(appRelHistory.updateDateTime, 'yyyy-MM-ddThh:mm');
                this.appRelHistoryModalRef(component, appRelHistory);
            });
        } else {
            return this.appRelHistoryModalRef(component, new AppRelHistory());
        }
    }

    appRelHistoryModalRef(component: Component, appRelHistory: AppRelHistory): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.appRelHistory = appRelHistory;
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
