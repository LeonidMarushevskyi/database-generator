import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { LicensureHistoryComponent } from './licensure-history.component';
import { LicensureHistoryDetailComponent } from './licensure-history-detail.component';
import { LicensureHistoryPopupComponent } from './licensure-history-dialog.component';
import { LicensureHistoryDeletePopupComponent } from './licensure-history-delete-dialog.component';

import { Principal } from '../../shared';

export const licensureHistoryRoute: Routes = [
    {
        path: 'licensure-history',
        component: LicensureHistoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LicensureHistories'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'licensure-history/:id',
        component: LicensureHistoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LicensureHistories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const licensureHistoryPopupRoute: Routes = [
    {
        path: 'licensure-history-new',
        component: LicensureHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LicensureHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'licensure-history/:id/edit',
        component: LicensureHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LicensureHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'licensure-history/:id/delete',
        component: LicensureHistoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'LicensureHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
