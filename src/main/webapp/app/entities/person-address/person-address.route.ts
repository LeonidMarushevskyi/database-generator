import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PersonAddressComponent } from './person-address.component';
import { PersonAddressDetailComponent } from './person-address-detail.component';
import { PersonAddressPopupComponent } from './person-address-dialog.component';
import { PersonAddressDeletePopupComponent } from './person-address-delete-dialog.component';

import { Principal } from '../../shared';

export const personAddressRoute: Routes = [
    {
        path: 'person-address',
        component: PersonAddressComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonAddresses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'person-address/:id',
        component: PersonAddressDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonAddresses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personAddressPopupRoute: Routes = [
    {
        path: 'person-address-new',
        component: PersonAddressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonAddresses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'person-address/:id/edit',
        component: PersonAddressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonAddresses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'person-address/:id/delete',
        component: PersonAddressDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonAddresses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
