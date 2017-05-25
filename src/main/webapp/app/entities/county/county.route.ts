import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CountyComponent } from './county.component';
import { CountyDetailComponent } from './county-detail.component';
import { CountyPopupComponent } from './county-dialog.component';
import { CountyDeletePopupComponent } from './county-delete-dialog.component';

import { Principal } from '../../shared';

export const countyRoute: Routes = [
    {
        path: 'county',
        component: CountyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Counties'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'county/:id',
        component: CountyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Counties'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const countyPopupRoute: Routes = [
    {
        path: 'county-new',
        component: CountyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Counties'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'county/:id/edit',
        component: CountyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Counties'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'county/:id/delete',
        component: CountyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Counties'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
