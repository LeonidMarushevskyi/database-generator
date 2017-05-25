import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DeficiencyComponent } from './deficiency.component';
import { DeficiencyDetailComponent } from './deficiency-detail.component';
import { DeficiencyPopupComponent } from './deficiency-dialog.component';
import { DeficiencyDeletePopupComponent } from './deficiency-delete-dialog.component';

import { Principal } from '../../shared';

export const deficiencyRoute: Routes = [
    {
        path: 'deficiency',
        component: DeficiencyComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Deficiencies'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'deficiency/:id',
        component: DeficiencyDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Deficiencies'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const deficiencyPopupRoute: Routes = [
    {
        path: 'deficiency-new',
        component: DeficiencyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Deficiencies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'deficiency/:id/edit',
        component: DeficiencyPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Deficiencies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'deficiency/:id/delete',
        component: DeficiencyDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Deficiencies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
