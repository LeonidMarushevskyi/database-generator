import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { RelationshipTypeComponent } from './relationship-type.component';
import { RelationshipTypeDetailComponent } from './relationship-type-detail.component';
import { RelationshipTypePopupComponent } from './relationship-type-dialog.component';
import { RelationshipTypeDeletePopupComponent } from './relationship-type-delete-dialog.component';

import { Principal } from '../../shared';

export const relationshipTypeRoute: Routes = [
    {
        path: 'relationship-type',
        component: RelationshipTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RelationshipTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'relationship-type/:id',
        component: RelationshipTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RelationshipTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const relationshipTypePopupRoute: Routes = [
    {
        path: 'relationship-type-new',
        component: RelationshipTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RelationshipTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'relationship-type/:id/edit',
        component: RelationshipTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RelationshipTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'relationship-type/:id/delete',
        component: RelationshipTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RelationshipTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
