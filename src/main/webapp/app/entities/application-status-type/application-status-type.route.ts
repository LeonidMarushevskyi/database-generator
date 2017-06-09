import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ApplicationStatusTypeComponent } from './application-status-type.component';
import { ApplicationStatusTypeDetailComponent } from './application-status-type-detail.component';
import { ApplicationStatusTypePopupComponent } from './application-status-type-dialog.component';
import { ApplicationStatusTypeDeletePopupComponent } from './application-status-type-delete-dialog.component';

import { Principal } from '../../shared';

export const applicationStatusTypeRoute: Routes = [
    {
        path: 'application-status-type',
        component: ApplicationStatusTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ApplicationStatusTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'application-status-type/:id',
        component: ApplicationStatusTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ApplicationStatusTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const applicationStatusTypePopupRoute: Routes = [
    {
        path: 'application-status-type-new',
        component: ApplicationStatusTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ApplicationStatusTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'application-status-type/:id/edit',
        component: ApplicationStatusTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ApplicationStatusTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'application-status-type/:id/delete',
        component: ApplicationStatusTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'ApplicationStatusTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
