import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { HouseholdComponent } from './household.component';
import { HouseholdDetailComponent } from './household-detail.component';
import { HouseholdPopupComponent } from './household-dialog.component';
import { HouseholdDeletePopupComponent } from './household-delete-dialog.component';

import { Principal } from '../../shared';

export const householdRoute: Routes = [
    {
        path: 'household',
        component: HouseholdComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Households'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'household/:id',
        component: HouseholdDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Households'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const householdPopupRoute: Routes = [
    {
        path: 'household-new',
        component: HouseholdPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Households'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'household/:id/edit',
        component: HouseholdPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Households'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'household/:id/delete',
        component: HouseholdDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Households'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
