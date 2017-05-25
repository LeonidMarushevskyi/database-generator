import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { InspectionComponent } from './inspection.component';
import { InspectionDetailComponent } from './inspection-detail.component';
import { InspectionPopupComponent } from './inspection-dialog.component';
import { InspectionDeletePopupComponent } from './inspection-delete-dialog.component';

import { Principal } from '../../shared';

export const inspectionRoute: Routes = [
    {
        path: 'inspection',
        component: InspectionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Inspections'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'inspection/:id',
        component: InspectionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Inspections'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const inspectionPopupRoute: Routes = [
    {
        path: 'inspection-new',
        component: InspectionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Inspections'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inspection/:id/edit',
        component: InspectionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Inspections'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'inspection/:id/delete',
        component: InspectionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Inspections'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
