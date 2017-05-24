import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AComponent } from './a.component';
import { ADetailComponent } from './a-detail.component';
import { APopupComponent } from './a-dialog.component';
import { ADeletePopupComponent } from './a-delete-dialog.component';

import { Principal } from '../../shared';


export const aRoute: Routes = [
  {
    path: 'a',
    component: AComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AS'
    }
  }, {
    path: 'a/:id',
    component: ADetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AS'
    }
  }
];

export const aPopupRoute: Routes = [
  {
    path: 'a-new',
    component: APopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AS'
    },
    outlet: 'popup'
  },
  {
    path: 'a/:id/edit',
    component: APopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AS'
    },
    outlet: 'popup'
  },
  {
    path: 'a/:id/delete',
    component: ADeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AS'
    },
    outlet: 'popup'
  }
];
