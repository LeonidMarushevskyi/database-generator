import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PersonPhoneComponent } from './person-phone.component';
import { PersonPhoneDetailComponent } from './person-phone-detail.component';
import { PersonPhonePopupComponent } from './person-phone-dialog.component';
import { PersonPhoneDeletePopupComponent } from './person-phone-delete-dialog.component';

import { Principal } from '../../shared';


export const personPhoneRoute: Routes = [
  {
    path: 'person-phone',
    component: PersonPhoneComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PersonPhones'
    }
  }, {
    path: 'person-phone/:id',
    component: PersonPhoneDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PersonPhones'
    }
  }
];

export const personPhonePopupRoute: Routes = [
  {
    path: 'person-phone-new',
    component: PersonPhonePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PersonPhones'
    },
    outlet: 'popup'
  },
  {
    path: 'person-phone/:id/edit',
    component: PersonPhonePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PersonPhones'
    },
    outlet: 'popup'
  },
  {
    path: 'person-phone/:id/delete',
    component: PersonPhoneDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PersonPhones'
    },
    outlet: 'popup'
  }
];
