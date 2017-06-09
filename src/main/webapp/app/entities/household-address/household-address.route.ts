import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { HouseholdAddressComponent } from './household-address.component';
import { HouseholdAddressDetailComponent } from './household-address-detail.component';
import { HouseholdAddressPopupComponent } from './household-address-dialog.component';
import { HouseholdAddressDeletePopupComponent } from './household-address-delete-dialog.component';

import { Principal } from '../../shared';

export const householdAddressRoute: Routes = [
    {
        path: 'household-address',
        component: HouseholdAddressComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseholdAddresses'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'household-address/:id',
        component: HouseholdAddressDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseholdAddresses'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const householdAddressPopupRoute: Routes = [
    {
        path: 'household-address-new',
        component: HouseholdAddressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseholdAddresses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'household-address/:id/edit',
        component: HouseholdAddressPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseholdAddresses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'household-address/:id/delete',
        component: HouseholdAddressDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseholdAddresses'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
