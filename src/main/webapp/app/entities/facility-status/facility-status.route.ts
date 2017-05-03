import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FacilityStatusComponent } from './facility-status.component';
import { FacilityStatusDetailComponent } from './facility-status-detail.component';
import { FacilityStatusPopupComponent } from './facility-status-dialog.component';
import { FacilityStatusDeletePopupComponent } from './facility-status-delete-dialog.component';

import { Principal } from '../../shared';


export const facilityStatusRoute: Routes = [
  {
    path: 'facility-status',
    component: FacilityStatusComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityStatuses'
    }
  }, {
    path: 'facility-status/:id',
    component: FacilityStatusDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityStatuses'
    }
  }
];

export const facilityStatusPopupRoute: Routes = [
  {
    path: 'facility-status-new',
    component: FacilityStatusPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityStatuses'
    },
    outlet: 'popup'
  },
  {
    path: 'facility-status/:id/edit',
    component: FacilityStatusPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityStatuses'
    },
    outlet: 'popup'
  },
  {
    path: 'facility-status/:id/delete',
    component: FacilityStatusDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityStatuses'
    },
    outlet: 'popup'
  }
];
