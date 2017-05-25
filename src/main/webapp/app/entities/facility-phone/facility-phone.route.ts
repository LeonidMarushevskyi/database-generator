import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FacilityPhoneComponent } from './facility-phone.component';
import { FacilityPhoneDetailComponent } from './facility-phone-detail.component';
import { FacilityPhonePopupComponent } from './facility-phone-dialog.component';
import { FacilityPhoneDeletePopupComponent } from './facility-phone-delete-dialog.component';

import { Principal } from '../../shared';

export const facilityPhoneRoute: Routes = [
    {
        path: 'facility-phone',
        component: FacilityPhoneComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FacilityPhones'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'facility-phone/:id',
        component: FacilityPhoneDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FacilityPhones'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const facilityPhonePopupRoute: Routes = [
    {
        path: 'facility-phone-new',
        component: FacilityPhonePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FacilityPhones'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'facility-phone/:id/edit',
        component: FacilityPhonePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FacilityPhones'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'facility-phone/:id/delete',
        component: FacilityPhoneDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'FacilityPhones'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
