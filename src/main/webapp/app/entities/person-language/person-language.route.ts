import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PersonLanguageComponent } from './person-language.component';
import { PersonLanguageDetailComponent } from './person-language-detail.component';
import { PersonLanguagePopupComponent } from './person-language-dialog.component';
import { PersonLanguageDeletePopupComponent } from './person-language-delete-dialog.component';

import { Principal } from '../../shared';


export const personLanguageRoute: Routes = [
  {
    path: 'person-language',
    component: PersonLanguageComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PersonLanguages'
    }
  }, {
    path: 'person-language/:id',
    component: PersonLanguageDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PersonLanguages'
    }
  }
];

export const personLanguagePopupRoute: Routes = [
  {
    path: 'person-language-new',
    component: PersonLanguagePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PersonLanguages'
    },
    outlet: 'popup'
  },
  {
    path: 'person-language/:id/edit',
    component: PersonLanguagePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PersonLanguages'
    },
    outlet: 'popup'
  },
  {
    path: 'person-language/:id/delete',
    component: PersonLanguageDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'PersonLanguages'
    },
    outlet: 'popup'
  }
];
