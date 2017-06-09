import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { EmailAddressComponent } from './email-address.component';
import { EmailAddressDetailComponent } from './email-address-detail.component';
import { EmailAddressPopupComponent } from './email-address-dialog.component';
import { EmailAddressDeletePopupComponent } from './email-address-delete-dialog.component';

import { Principal } from '../../shared';

export const emailAddressRoute: Routes = [
    {
        path: 'email-address',
        component: EmailAddressComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmailAddresses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'email-address/:id',
        component: EmailAddressDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmailAddresses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const emailAddressPopupRoute: Routes = [
    {
        path: 'email-address-new',
        component: EmailAddressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmailAddresses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'email-address/:id/edit',
        component: EmailAddressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmailAddresses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'email-address/:id/delete',
        component: EmailAddressDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EmailAddresses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
