import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ChildPreferencesComponent } from './child-preferences.component';
import { ChildPreferencesDetailComponent } from './child-preferences-detail.component';
import { ChildPreferencesPopupComponent } from './child-preferences-dialog.component';
import { ChildPreferencesDeletePopupComponent } from './child-preferences-delete-dialog.component';

import { Principal } from '../../shared';

export const childPreferencesRoute: Routes = [
    {
        path: 'child-preferences',
        component: ChildPreferencesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChildPreferences'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'child-preferences/:id',
        component: ChildPreferencesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChildPreferences'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const childPreferencesPopupRoute: Routes = [
    {
        path: 'child-preferences-new',
        component: ChildPreferencesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChildPreferences'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'child-preferences/:id/edit',
        component: ChildPreferencesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChildPreferences'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'child-preferences/:id/delete',
        component: ChildPreferencesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ChildPreferences'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
