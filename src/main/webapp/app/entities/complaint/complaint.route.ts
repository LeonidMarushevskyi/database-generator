import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ComplaintComponent } from './complaint.component';
import { ComplaintDetailComponent } from './complaint-detail.component';
import { ComplaintPopupComponent } from './complaint-dialog.component';
import { ComplaintDeletePopupComponent } from './complaint-delete-dialog.component';

import { Principal } from '../../shared';

export const complaintRoute: Routes = [
    {
        path: 'complaint',
        component: ComplaintComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Complaints'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'complaint/:id',
        component: ComplaintDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Complaints'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const complaintPopupRoute: Routes = [
    {
        path: 'complaint-new',
        component: ComplaintPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Complaints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'complaint/:id/edit',
        component: ComplaintPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Complaints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'complaint/:id/delete',
        component: ComplaintDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Complaints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
