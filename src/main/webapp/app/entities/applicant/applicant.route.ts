import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { ApplicantComponent } from './applicant.component';
import { ApplicantDetailComponent } from './applicant-detail.component';
import { ApplicantPopupComponent } from './applicant-dialog.component';
import { ApplicantDeletePopupComponent } from './applicant-delete-dialog.component';

import { Principal } from '../../shared';

export const applicantRoute: Routes = [
    {
        path: 'applicant',
        component: ApplicantComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applicants'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'applicant/:id',
        component: ApplicantDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applicants'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const applicantPopupRoute: Routes = [
    {
        path: 'applicant-new',
        component: ApplicantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applicants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'applicant/:id/edit',
        component: ApplicantPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applicants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'applicant/:id/delete',
        component: ApplicantDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Applicants'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
