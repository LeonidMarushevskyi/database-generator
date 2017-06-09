import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { SiblingGroupTypeComponent } from './sibling-group-type.component';
import { SiblingGroupTypeDetailComponent } from './sibling-group-type-detail.component';
import { SiblingGroupTypePopupComponent } from './sibling-group-type-dialog.component';
import { SiblingGroupTypeDeletePopupComponent } from './sibling-group-type-delete-dialog.component';

import { Principal } from '../../shared';

export const siblingGroupTypeRoute: Routes = [
    {
        path: 'sibling-group-type',
        component: SiblingGroupTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiblingGroupTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'sibling-group-type/:id',
        component: SiblingGroupTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiblingGroupTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const siblingGroupTypePopupRoute: Routes = [
    {
        path: 'sibling-group-type-new',
        component: SiblingGroupTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiblingGroupTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sibling-group-type/:id/edit',
        component: SiblingGroupTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiblingGroupTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'sibling-group-type/:id/delete',
        component: SiblingGroupTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'SiblingGroupTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
