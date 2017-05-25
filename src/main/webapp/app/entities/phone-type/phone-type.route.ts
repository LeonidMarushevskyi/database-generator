import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PhoneTypeComponent } from './phone-type.component';
import { PhoneTypeDetailComponent } from './phone-type-detail.component';
import { PhoneTypePopupComponent } from './phone-type-dialog.component';
import { PhoneTypeDeletePopupComponent } from './phone-type-delete-dialog.component';

import { Principal } from '../../shared';

export const phoneTypeRoute: Routes = [
    {
        path: 'phone-type',
        component: PhoneTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PhoneTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'phone-type/:id',
        component: PhoneTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PhoneTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const phoneTypePopupRoute: Routes = [
    {
        path: 'phone-type-new',
        component: PhoneTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PhoneTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'phone-type/:id/edit',
        component: PhoneTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PhoneTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'phone-type/:id/delete',
        component: PhoneTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PhoneTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
