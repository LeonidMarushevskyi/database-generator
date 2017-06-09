import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { StateTypeComponent } from './state-type.component';
import { StateTypeDetailComponent } from './state-type-detail.component';
import { StateTypePopupComponent } from './state-type-dialog.component';
import { StateTypeDeletePopupComponent } from './state-type-delete-dialog.component';

import { Principal } from '../../shared';

export const stateTypeRoute: Routes = [
    {
        path: 'state-type',
        component: StateTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StateTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'state-type/:id',
        component: StateTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StateTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const stateTypePopupRoute: Routes = [
    {
        path: 'state-type-new',
        component: StateTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StateTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'state-type/:id/edit',
        component: StateTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StateTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'state-type/:id/delete',
        component: StateTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'StateTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
