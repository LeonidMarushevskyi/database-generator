import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CountyTypeComponent } from './county-type.component';
import { CountyTypeDetailComponent } from './county-type-detail.component';
import { CountyTypePopupComponent } from './county-type-dialog.component';
import { CountyTypeDeletePopupComponent } from './county-type-delete-dialog.component';

import { Principal } from '../../shared';

export const countyTypeRoute: Routes = [
    {
        path: 'county-type',
        component: CountyTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CountyTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'county-type/:id',
        component: CountyTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CountyTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const countyTypePopupRoute: Routes = [
    {
        path: 'county-type-new',
        component: CountyTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CountyTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'county-type/:id/edit',
        component: CountyTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CountyTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'county-type/:id/delete',
        component: CountyTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CountyTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
