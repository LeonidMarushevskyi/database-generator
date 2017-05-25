import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Complaint } from './complaint.model';
import { ComplaintService } from './complaint.service';
@Injectable()
export class ComplaintPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private complaintService: ComplaintService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.complaintService.find(id).subscribe((complaint) => {
                if (complaint.complaintDate) {
                    complaint.complaintDate = {
                        year: complaint.complaintDate.getFullYear(),
                        month: complaint.complaintDate.getMonth() + 1,
                        day: complaint.complaintDate.getDate()
                    };
                }
                if (complaint.approvalDate) {
                    complaint.approvalDate = {
                        year: complaint.approvalDate.getFullYear(),
                        month: complaint.approvalDate.getMonth() + 1,
                        day: complaint.approvalDate.getDate()
                    };
                }
                this.complaintModalRef(component, complaint);
            });
        } else {
            return this.complaintModalRef(component, new Complaint());
        }
    }

    complaintModalRef(component: Component, complaint: Complaint): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.complaint = complaint;
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
