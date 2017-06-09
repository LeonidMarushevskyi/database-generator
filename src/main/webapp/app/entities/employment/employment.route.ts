import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { EmploymentComponent } from './employment.component';
import { EmploymentDetailComponent } from './employment-detail.component';
import { EmploymentPopupComponent } from './employment-dialog.component';
import { EmploymentDeletePopupComponent } from './employment-delete-dialog.component';

import { Principal } from '../../shared';

export const employmentRoute: Routes = [
    {
        path: 'employment',
        component: EmploymentComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employments'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'employment/:id',
        component: EmploymentDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employments'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employmentPopupRoute: Routes = [
    {
        path: 'employment-new',
        component: EmploymentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employment/:id/edit',
        component: EmploymentPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employment/:id/delete',
        component: EmploymentDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employments'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
