import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PersonEthnicityComponent } from './person-ethnicity.component';
import { PersonEthnicityDetailComponent } from './person-ethnicity-detail.component';
import { PersonEthnicityPopupComponent } from './person-ethnicity-dialog.component';
import { PersonEthnicityDeletePopupComponent } from './person-ethnicity-delete-dialog.component';

import { Principal } from '../../shared';


export const personEthnicityRoute: Routes = [
  {
    path: 'person-ethnicity',
    component: PersonEthnicityComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PersonEthnicities'
    }
  }, {
    path: 'person-ethnicity/:id',
    component: PersonEthnicityDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PersonEthnicities'
    }
  }
];

export const personEthnicityPopupRoute: Routes = [
  {
    path: 'person-ethnicity-new',
    component: PersonEthnicityPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PersonEthnicities'
    },
    outlet: 'popup'
  },
  {
    path: 'person-ethnicity/:id/edit',
    component: PersonEthnicityPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PersonEthnicities'
    },
    outlet: 'popup'
  },
  {
    path: 'person-ethnicity/:id/delete',
    component: PersonEthnicityDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PersonEthnicities'
    },
    outlet: 'popup'
  }
];
