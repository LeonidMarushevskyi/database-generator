import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { EthnicityTypeComponent } from './ethnicity-type.component';
import { EthnicityTypeDetailComponent } from './ethnicity-type-detail.component';
import { EthnicityTypePopupComponent } from './ethnicity-type-dialog.component';
import { EthnicityTypeDeletePopupComponent } from './ethnicity-type-delete-dialog.component';

import { Principal } from '../../shared';


export const ethnicityTypeRoute: Routes = [
  {
    path: 'ethnicity-type',
    component: EthnicityTypeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'EthnicityTypes'
    }
  }, {
    path: 'ethnicity-type/:id',
    component: EthnicityTypeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'EthnicityTypes'
    }
  }
];

export const ethnicityTypePopupRoute: Routes = [
  {
    path: 'ethnicity-type-new',
    component: EthnicityTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'EthnicityTypes'
    },
    outlet: 'popup'
  },
  {
    path: 'ethnicity-type/:id/edit',
    component: EthnicityTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'EthnicityTypes'
    },
    outlet: 'popup'
  },
  {
    path: 'ethnicity-type/:id/delete',
    component: EthnicityTypeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'EthnicityTypes'
    },
    outlet: 'popup'
  }
];
