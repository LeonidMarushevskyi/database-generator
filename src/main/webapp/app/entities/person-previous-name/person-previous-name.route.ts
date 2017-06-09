import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PersonPreviousNameComponent } from './person-previous-name.component';
import { PersonPreviousNameDetailComponent } from './person-previous-name-detail.component';
import { PersonPreviousNamePopupComponent } from './person-previous-name-dialog.component';
import { PersonPreviousNameDeletePopupComponent } from './person-previous-name-delete-dialog.component';

import { Principal } from '../../shared';

export const personPreviousNameRoute: Routes = [
    {
        path: 'person-previous-name',
        component: PersonPreviousNameComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonPreviousNames'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'person-previous-name/:id',
        component: PersonPreviousNameDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonPreviousNames'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personPreviousNamePopupRoute: Routes = [
    {
        path: 'person-previous-name-new',
        component: PersonPreviousNamePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonPreviousNames'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'person-previous-name/:id/edit',
        component: PersonPreviousNamePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonPreviousNames'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'person-previous-name/:id/delete',
        component: PersonPreviousNameDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'PersonPreviousNames'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
