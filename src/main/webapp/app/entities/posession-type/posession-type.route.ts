import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PosessionTypeComponent } from './posession-type.component';
import { PosessionTypeDetailComponent } from './posession-type-detail.component';
import { PosessionTypePopupComponent } from './posession-type-dialog.component';
import { PosessionTypeDeletePopupComponent } from './posession-type-delete-dialog.component';

import { Principal } from '../../shared';

export const posessionTypeRoute: Routes = [
    {
        path: 'posession-type',
        component: PosessionTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PosessionTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'posession-type/:id',
        component: PosessionTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PosessionTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const posessionTypePopupRoute: Routes = [
    {
        path: 'posession-type-new',
        component: PosessionTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PosessionTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'posession-type/:id/edit',
        component: PosessionTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PosessionTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'posession-type/:id/delete',
        component: PosessionTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PosessionTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
