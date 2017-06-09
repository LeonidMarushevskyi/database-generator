import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { BodyOfWaterComponent } from './body-of-water.component';
import { BodyOfWaterDetailComponent } from './body-of-water-detail.component';
import { BodyOfWaterPopupComponent } from './body-of-water-dialog.component';
import { BodyOfWaterDeletePopupComponent } from './body-of-water-delete-dialog.component';

import { Principal } from '../../shared';

export const bodyOfWaterRoute: Routes = [
    {
        path: 'body-of-water',
        component: BodyOfWaterComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BodyOfWaters'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'body-of-water/:id',
        component: BodyOfWaterDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BodyOfWaters'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const bodyOfWaterPopupRoute: Routes = [
    {
        path: 'body-of-water-new',
        component: BodyOfWaterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BodyOfWaters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'body-of-water/:id/edit',
        component: BodyOfWaterPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BodyOfWaters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'body-of-water/:id/delete',
        component: BodyOfWaterDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'BodyOfWaters'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
