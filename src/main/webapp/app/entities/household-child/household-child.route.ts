import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { HouseholdChildComponent } from './household-child.component';
import { HouseholdChildDetailComponent } from './household-child-detail.component';
import { HouseholdChildPopupComponent } from './household-child-dialog.component';
import { HouseholdChildDeletePopupComponent } from './household-child-delete-dialog.component';

import { Principal } from '../../shared';

export const householdChildRoute: Routes = [
    {
        path: 'household-child',
        component: HouseholdChildComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseholdChildren'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'household-child/:id',
        component: HouseholdChildDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseholdChildren'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const householdChildPopupRoute: Routes = [
    {
        path: 'household-child-new',
        component: HouseholdChildPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseholdChildren'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'household-child/:id/edit',
        component: HouseholdChildPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseholdChildren'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'household-child/:id/delete',
        component: HouseholdChildDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseholdChildren'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
