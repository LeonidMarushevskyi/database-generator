import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DeterminedChildComponent } from './determined-child.component';
import { DeterminedChildDetailComponent } from './determined-child-detail.component';
import { DeterminedChildPopupComponent } from './determined-child-dialog.component';
import { DeterminedChildDeletePopupComponent } from './determined-child-delete-dialog.component';

import { Principal } from '../../shared';

export const determinedChildRoute: Routes = [
    {
        path: 'determined-child',
        component: DeterminedChildComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeterminedChildren'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'determined-child/:id',
        component: DeterminedChildDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeterminedChildren'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const determinedChildPopupRoute: Routes = [
    {
        path: 'determined-child-new',
        component: DeterminedChildPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeterminedChildren'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'determined-child/:id/edit',
        component: DeterminedChildPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeterminedChildren'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'determined-child/:id/delete',
        component: DeterminedChildDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'DeterminedChildren'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
