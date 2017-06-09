import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { GenderTypeComponent } from './gender-type.component';
import { GenderTypeDetailComponent } from './gender-type-detail.component';
import { GenderTypePopupComponent } from './gender-type-dialog.component';
import { GenderTypeDeletePopupComponent } from './gender-type-delete-dialog.component';

import { Principal } from '../../shared';

export const genderTypeRoute: Routes = [
    {
        path: 'gender-type',
        component: GenderTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GenderTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'gender-type/:id',
        component: GenderTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GenderTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const genderTypePopupRoute: Routes = [
    {
        path: 'gender-type-new',
        component: GenderTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GenderTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gender-type/:id/edit',
        component: GenderTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GenderTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'gender-type/:id/delete',
        component: GenderTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'GenderTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
