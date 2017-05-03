import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { DistrictOfficeComponent } from './district-office.component';
import { DistrictOfficeDetailComponent } from './district-office-detail.component';
import { DistrictOfficePopupComponent } from './district-office-dialog.component';
import { DistrictOfficeDeletePopupComponent } from './district-office-delete-dialog.component';

import { Principal } from '../../shared';


export const districtOfficeRoute: Routes = [
  {
    path: 'district-office',
    component: DistrictOfficeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'DistrictOffices'
    }
  }, {
    path: 'district-office/:id',
    component: DistrictOfficeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'DistrictOffices'
    }
  }
];

export const districtOfficePopupRoute: Routes = [
  {
    path: 'district-office-new',
    component: DistrictOfficePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'DistrictOffices'
    },
    outlet: 'popup'
  },
  {
    path: 'district-office/:id/edit',
    component: DistrictOfficePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'DistrictOffices'
    },
    outlet: 'popup'
  },
  {
    path: 'district-office/:id/delete',
    component: DistrictOfficeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'DistrictOffices'
    },
    outlet: 'popup'
  }
];
