import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { RaceTypeComponent } from './race-type.component';
import { RaceTypeDetailComponent } from './race-type-detail.component';
import { RaceTypePopupComponent } from './race-type-dialog.component';
import { RaceTypeDeletePopupComponent } from './race-type-delete-dialog.component';

import { Principal } from '../../shared';

export const raceTypeRoute: Routes = [
    {
        path: 'race-type',
        component: RaceTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RaceTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'race-type/:id',
        component: RaceTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RaceTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const raceTypePopupRoute: Routes = [
    {
        path: 'race-type-new',
        component: RaceTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RaceTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'race-type/:id/edit',
        component: RaceTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RaceTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'race-type/:id/delete',
        component: RaceTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RaceTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
