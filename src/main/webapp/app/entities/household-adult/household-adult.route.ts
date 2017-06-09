import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { HouseholdAdultComponent } from './household-adult.component';
import { HouseholdAdultDetailComponent } from './household-adult-detail.component';
import { HouseholdAdultPopupComponent } from './household-adult-dialog.component';
import { HouseholdAdultDeletePopupComponent } from './household-adult-delete-dialog.component';

import { Principal } from '../../shared';

export const householdAdultRoute: Routes = [
    {
        path: 'household-adult',
        component: HouseholdAdultComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseholdAdults'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'household-adult/:id',
        component: HouseholdAdultDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseholdAdults'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const householdAdultPopupRoute: Routes = [
    {
        path: 'household-adult-new',
        component: HouseholdAdultPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseholdAdults'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'household-adult/:id/edit',
        component: HouseholdAdultPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseholdAdults'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'household-adult/:id/delete',
        component: HouseholdAdultDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'HouseholdAdults'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
