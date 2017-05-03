import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AssignedWorkerComponent } from './assigned-worker.component';
import { AssignedWorkerDetailComponent } from './assigned-worker-detail.component';
import { AssignedWorkerPopupComponent } from './assigned-worker-dialog.component';
import { AssignedWorkerDeletePopupComponent } from './assigned-worker-delete-dialog.component';

import { Principal } from '../../shared';


export const assignedWorkerRoute: Routes = [
  {
    path: 'assigned-worker',
    component: AssignedWorkerComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AssignedWorkers'
    }
  }, {
    path: 'assigned-worker/:id',
    component: AssignedWorkerDetailComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AssignedWorkers'
    }
  }
];

export const assignedWorkerPopupRoute: Routes = [
  {
    path: 'assigned-worker-new',
    component: AssignedWorkerPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AssignedWorkers'
    },
    outlet: 'popup'
  },
  {
    path: 'assigned-worker/:id/edit',
    component: AssignedWorkerPopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AssignedWorkers'
    },
    outlet: 'popup'
  },
  {
    path: 'assigned-worker/:id/delete',
    component: AssignedWorkerDeletePopupComponent,
    data: {
        authorities: ['ROLE_USER'],
        pageTitle: 'AssignedWorkers'
    },
    outlet: 'popup'
  }
];
