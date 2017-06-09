import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AppRelHistoryComponent } from './app-rel-history.component';
import { AppRelHistoryDetailComponent } from './app-rel-history-detail.component';
import { AppRelHistoryPopupComponent } from './app-rel-history-dialog.component';
import { AppRelHistoryDeletePopupComponent } from './app-rel-history-delete-dialog.component';

import { Principal } from '../../shared';

export const appRelHistoryRoute: Routes = [
    {
        path: 'app-rel-history',
        component: AppRelHistoryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AppRelHistories'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'app-rel-history/:id',
        component: AppRelHistoryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AppRelHistories'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const appRelHistoryPopupRoute: Routes = [
    {
        path: 'app-rel-history-new',
        component: AppRelHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AppRelHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'app-rel-history/:id/edit',
        component: AppRelHistoryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AppRelHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'app-rel-history/:id/delete',
        component: AppRelHistoryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AppRelHistories'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
