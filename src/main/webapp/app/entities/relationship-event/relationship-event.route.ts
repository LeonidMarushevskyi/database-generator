import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { RelationshipEventComponent } from './relationship-event.component';
import { RelationshipEventDetailComponent } from './relationship-event-detail.component';
import { RelationshipEventPopupComponent } from './relationship-event-dialog.component';
import { RelationshipEventDeletePopupComponent } from './relationship-event-delete-dialog.component';

import { Principal } from '../../shared';

export const relationshipEventRoute: Routes = [
    {
        path: 'relationship-event',
        component: RelationshipEventComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RelationshipEvents'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'relationship-event/:id',
        component: RelationshipEventDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RelationshipEvents'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const relationshipEventPopupRoute: Routes = [
    {
        path: 'relationship-event-new',
        component: RelationshipEventPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RelationshipEvents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'relationship-event/:id/edit',
        component: RelationshipEventPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RelationshipEvents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'relationship-event/:id/delete',
        component: RelationshipEventDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'RelationshipEvents'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
