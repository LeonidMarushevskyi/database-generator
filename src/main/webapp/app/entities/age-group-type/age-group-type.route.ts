import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AgeGroupTypeComponent } from './age-group-type.component';
import { AgeGroupTypeDetailComponent } from './age-group-type-detail.component';
import { AgeGroupTypePopupComponent } from './age-group-type-dialog.component';
import { AgeGroupTypeDeletePopupComponent } from './age-group-type-delete-dialog.component';

import { Principal } from '../../shared';

export const ageGroupTypeRoute: Routes = [
    {
        path: 'age-group-type',
        component: AgeGroupTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AgeGroupTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'age-group-type/:id',
        component: AgeGroupTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AgeGroupTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ageGroupTypePopupRoute: Routes = [
    {
        path: 'age-group-type-new',
        component: AgeGroupTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AgeGroupTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'age-group-type/:id/edit',
        component: AgeGroupTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AgeGroupTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'age-group-type/:id/delete',
        component: AgeGroupTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'AgeGroupTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
