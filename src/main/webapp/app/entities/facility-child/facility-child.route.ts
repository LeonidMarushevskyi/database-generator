import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FacilityChildComponent } from './facility-child.component';
import { FacilityChildDetailComponent } from './facility-child-detail.component';
import { FacilityChildPopupComponent } from './facility-child-dialog.component';
import { FacilityChildDeletePopupComponent } from './facility-child-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class FacilityChildResolvePagingParams implements Resolve<any> {

  constructor(private paginationUtil: PaginationUtil) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
      let page = route.queryParams['page'] ? route.queryParams['page'] : '1';
      let sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
      return {
          page: this.paginationUtil.parsePage(page),
          predicate: this.paginationUtil.parsePredicate(sort),
          ascending: this.paginationUtil.parseAscending(sort)
    };
  }
}

export const facilityChildRoute: Routes = [
  {
    path: 'facility-child',
    component: FacilityChildComponent,
    resolve: {
      'pagingParams': FacilityChildResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityChildren'
    }
  }, {
    path: 'facility-child/:id',
    component: FacilityChildDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityChildren'
    }
  }
];

export const facilityChildPopupRoute: Routes = [
  {
    path: 'facility-child-new',
    component: FacilityChildPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityChildren'
    },
    outlet: 'popup'
  },
  {
    path: 'facility-child/:id/edit',
    component: FacilityChildPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityChildren'
    },
    outlet: 'popup'
  },
  {
    path: 'facility-child/:id/delete',
    component: FacilityChildDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'FacilityChildren'
    },
    outlet: 'popup'
  }
];
