import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Inspection } from './inspection.model';
import { InspectionService } from './inspection.service';
@Injectable()
export class InspectionPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private inspectionService: InspectionService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.inspectionService.find(id).subscribe((inspection) => {
                if (inspection.representativeSignatureDate) {
                    inspection.representativeSignatureDate = {
                        year: inspection.representativeSignatureDate.getFullYear(),
                        month: inspection.representativeSignatureDate.getMonth() + 1,
                        day: inspection.representativeSignatureDate.getDate()
                    };
                }
                if (inspection.form809PrintDate) {
                    inspection.form809PrintDate = {
                        year: inspection.form809PrintDate.getFullYear(),
                        month: inspection.form809PrintDate.getMonth() + 1,
                        day: inspection.form809PrintDate.getDate()
                    };
                }
                this.inspectionModalRef(component, inspection);
            });
        } else {
            return this.inspectionModalRef(component, new Inspection());
        }
    }

    inspectionModalRef(component: Component, inspection: Inspection): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.inspection = inspection;
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
