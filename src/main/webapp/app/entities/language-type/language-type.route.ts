import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { LanguageTypeComponent } from './language-type.component';
import { LanguageTypeDetailComponent } from './language-type-detail.component';
import { LanguageTypePopupComponent } from './language-type-dialog.component';
import { LanguageTypeDeletePopupComponent } from './language-type-delete-dialog.component';

import { Principal } from '../../shared';


export const languageTypeRoute: Routes = [
  {
    path: 'language-type',
    component: LanguageTypeComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'LanguageTypes'
    }
  }, {
    path: 'language-type/:id',
    component: LanguageTypeDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'LanguageTypes'
    }
  }
];

export const languageTypePopupRoute: Routes = [
  {
    path: 'language-type-new',
    component: LanguageTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'LanguageTypes'
    },
    outlet: 'popup'
  },
  {
    path: 'language-type/:id/edit',
    component: LanguageTypePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'LanguageTypes'
    },
    outlet: 'popup'
  },
  {
    path: 'language-type/:id/delete',
    component: LanguageTypeDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'LanguageTypes'
    },
    outlet: 'popup'
  }
];
