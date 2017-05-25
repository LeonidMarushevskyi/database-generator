import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AddressTypeComponent } from './address-type.component';
import { AddressTypeDetailComponent } from './address-type-detail.component';
import { AddressTypePopupComponent } from './address-type-dialog.component';
import { AddressTypeDeletePopupComponent } from './address-type-delete-dialog.component';

import { Principal } from '../../shared';

export const addressTypeRoute: Routes = [
    {
        path: 'address-type',
        component: AddressTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AddressTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'address-type/:id',
        component: AddressTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AddressTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const addressTypePopupRoute: Routes = [
    {
        path: 'address-type-new',
        component: AddressTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AddressTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'address-type/:id/edit',
        component: AddressTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AddressTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'address-type/:id/delete',
        component: AddressTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AddressTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
