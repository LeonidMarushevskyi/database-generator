import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PhoneComponent } from './phone.component';
import { PhoneDetailComponent } from './phone-detail.component';
import { PhonePopupComponent } from './phone-dialog.component';
import { PhoneDeletePopupComponent } from './phone-delete-dialog.component';

import { Principal } from '../../shared';

export const phoneRoute: Routes = [
    {
        path: 'phone',
        component: PhoneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Phones'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'phone/:id',
        component: PhoneDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Phones'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const phonePopupRoute: Routes = [
    {
        path: 'phone-new',
        component: PhonePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Phones'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'phone/:id/edit',
        component: PhonePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Phones'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'phone/:id/delete',
        component: PhoneDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Phones'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
