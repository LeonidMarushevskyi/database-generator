import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PhoneNumberTypeComponent } from './phone-number-type.component';
import { PhoneNumberTypeDetailComponent } from './phone-number-type-detail.component';
import { PhoneNumberTypePopupComponent } from './phone-number-type-dialog.component';
import { PhoneNumberTypeDeletePopupComponent } from './phone-number-type-delete-dialog.component';

import { Principal } from '../../shared';

export const phoneNumberTypeRoute: Routes = [
    {
        path: 'phone-number-type',
        component: PhoneNumberTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PhoneNumberTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'phone-number-type/:id',
        component: PhoneNumberTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PhoneNumberTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const phoneNumberTypePopupRoute: Routes = [
    {
        path: 'phone-number-type-new',
        component: PhoneNumberTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PhoneNumberTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'phone-number-type/:id/edit',
        component: PhoneNumberTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PhoneNumberTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'phone-number-type/:id/delete',
        component: PhoneNumberTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PhoneNumberTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
