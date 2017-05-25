import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PersonRaceComponent } from './person-race.component';
import { PersonRaceDetailComponent } from './person-race-detail.component';
import { PersonRacePopupComponent } from './person-race-dialog.component';
import { PersonRaceDeletePopupComponent } from './person-race-delete-dialog.component';

import { Principal } from '../../shared';

export const personRaceRoute: Routes = [
    {
        path: 'person-race',
        component: PersonRaceComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonRaces'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'person-race/:id',
        component: PersonRaceDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonRaces'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personRacePopupRoute: Routes = [
    {
        path: 'person-race-new',
        component: PersonRacePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonRaces'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'person-race/:id/edit',
        component: PersonRacePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonRaces'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'person-race/:id/delete',
        component: PersonRaceDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonRaces'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
