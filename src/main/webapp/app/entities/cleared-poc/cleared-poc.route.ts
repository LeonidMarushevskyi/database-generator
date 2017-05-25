import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ClearedPOCComponent } from './cleared-poc.component';
import { ClearedPOCDetailComponent } from './cleared-poc-detail.component';
import { ClearedPOCPopupComponent } from './cleared-poc-dialog.component';
import { ClearedPOCDeletePopupComponent } from './cleared-poc-delete-dialog.component';

import { Principal } from '../../shared';

export const clearedPOCRoute: Routes = [
    {
        path: 'cleared-poc',
        component: ClearedPOCComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClearedPOCS'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'cleared-poc/:id',
        component: ClearedPOCDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClearedPOCS'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const clearedPOCPopupRoute: Routes = [
    {
        path: 'cleared-poc-new',
        component: ClearedPOCPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClearedPOCS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cleared-poc/:id/edit',
        component: ClearedPOCPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClearedPOCS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'cleared-poc/:id/delete',
        component: ClearedPOCDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ClearedPOCS'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
