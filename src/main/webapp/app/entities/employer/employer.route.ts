import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { EmployerComponent } from './employer.component';
import { EmployerDetailComponent } from './employer-detail.component';
import { EmployerPopupComponent } from './employer-dialog.component';
import { EmployerDeletePopupComponent } from './employer-delete-dialog.component';

import { Principal } from '../../shared';

export const employerRoute: Routes = [
    {
        path: 'employer',
        component: EmployerComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employers'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'employer/:id',
        component: EmployerDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employers'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const employerPopupRoute: Routes = [
    {
        path: 'employer-new',
        component: EmployerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employer/:id/edit',
        component: EmployerPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'employer/:id/delete',
        component: EmployerDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Employers'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
