import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FacilityTypeComponent } from './facility-type.component';
import { FacilityTypeDetailComponent } from './facility-type-detail.component';
import { FacilityTypePopupComponent } from './facility-type-dialog.component';
import { FacilityTypeDeletePopupComponent } from './facility-type-delete-dialog.component';

import { Principal } from '../../shared';


export const facilityTypeRoute: Routes = [
  {
    path: 'facility-type',
    component: FacilityTypeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityTypes'
    }
  }, {
    path: 'facility-type/:id',
    component: FacilityTypeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityTypes'
    }
  }
];

export const facilityTypePopupRoute: Routes = [
  {
    path: 'facility-type-new',
    component: FacilityTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityTypes'
    },
    outlet: 'popup'
  },
  {
    path: 'facility-type/:id/edit',
    component: FacilityTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityTypes'
    },
    outlet: 'popup'
  },
  {
    path: 'facility-type/:id/delete',
    component: FacilityTypeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityTypes'
    },
    outlet: 'popup'
  }
];
