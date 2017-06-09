import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { RelationshipEventTypeComponent } from './relationship-event-type.component';
import { RelationshipEventTypeDetailComponent } from './relationship-event-type-detail.component';
import { RelationshipEventTypePopupComponent } from './relationship-event-type-dialog.component';
import { RelationshipEventTypeDeletePopupComponent } from './relationship-event-type-delete-dialog.component';

import { Principal } from '../../shared';

export const relationshipEventTypeRoute: Routes = [
    {
        path: 'relationship-event-type',
        component: RelationshipEventTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RelationshipEventTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'relationship-event-type/:id',
        component: RelationshipEventTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RelationshipEventTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const relationshipEventTypePopupRoute: Routes = [
    {
        path: 'relationship-event-type-new',
        component: RelationshipEventTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RelationshipEventTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'relationship-event-type/:id/edit',
        component: RelationshipEventTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RelationshipEventTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'relationship-event-type/:id/delete',
        component: RelationshipEventTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RelationshipEventTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
