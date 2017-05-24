import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { FacilityComponent } from './facility.component';
import { FacilityDetailComponent } from './facility-detail.component';
import { FacilityPopupComponent } from './facility-dialog.component';
import { FacilityDeletePopupComponent } from './facility-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class FacilityResolvePagingParams implements Resolve<any> {

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

export const facilityRoute: Routes = [
  {
    path: 'facility',
    component: FacilityComponent,
    resolve: {
      'pagingParams': FacilityResolvePagingParams
    },
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Facilities'
    }
  }, {
    path: 'facility/:id',
    component: FacilityDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Facilities'
    }
  }
];

export const facilityPopupRoute: Routes = [
  {
    path: 'facility-new',
    component: FacilityPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Facilities'
    },
    outlet: 'popup'
  },
  {
    path: 'facility/:id/edit',
    component: FacilityPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Facilities'
    },
    outlet: 'popup'
  },
  {
    path: 'facility/:id/delete',
    component: FacilityDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'Facilities'
    },
    outlet: 'popup'
  }
];
