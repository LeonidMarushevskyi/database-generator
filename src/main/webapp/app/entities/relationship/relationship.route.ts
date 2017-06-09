import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { RelationshipComponent } from './relationship.component';
import { RelationshipDetailComponent } from './relationship-detail.component';
import { RelationshipPopupComponent } from './relationship-dialog.component';
import { RelationshipDeletePopupComponent } from './relationship-delete-dialog.component';

import { Principal } from '../../shared';

export const relationshipRoute: Routes = [
    {
        path: 'relationship',
        component: RelationshipComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Relationships'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'relationship/:id',
        component: RelationshipDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Relationships'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const relationshipPopupRoute: Routes = [
    {
        path: 'relationship-new',
        component: RelationshipPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Relationships'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'relationship/:id/edit',
        component: RelationshipPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Relationships'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'relationship/:id/delete',
        component: RelationshipDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Relationships'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
