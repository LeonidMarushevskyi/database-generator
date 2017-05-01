import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FacilityAddressComponent } from './facility-address.component';
import { FacilityAddressDetailComponent } from './facility-address-detail.component';
import { FacilityAddressPopupComponent } from './facility-address-dialog.component';
import { FacilityAddressDeletePopupComponent } from './facility-address-delete-dialog.component';

import { Principal } from '../../shared';


export const facilityAddressRoute: Routes = [
  {
    path: 'facility-address',
    component: FacilityAddressComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityAddresses'
    }
  }, {
    path: 'facility-address/:id',
    component: FacilityAddressDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityAddresses'
    }
  }
];

export const facilityAddressPopupRoute: Routes = [
  {
    path: 'facility-address-new',
    component: FacilityAddressPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityAddresses'
    },
    outlet: 'popup'
  },
  {
    path: 'facility-address/:id/edit',
    component: FacilityAddressPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityAddresses'
    },
    outlet: 'popup'
  },
  {
    path: 'facility-address/:id/delete',
    component: FacilityAddressDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityAddresses'
    },
    outlet: 'popup'
  }
];
