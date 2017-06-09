import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { EducationPointComponent } from './education-point.component';
import { EducationPointDetailComponent } from './education-point-detail.component';
import { EducationPointPopupComponent } from './education-point-dialog.component';
import { EducationPointDeletePopupComponent } from './education-point-delete-dialog.component';

import { Principal } from '../../shared';

export const educationPointRoute: Routes = [
    {
        path: 'education-point',
        component: EducationPointComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EducationPoints'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'education-point/:id',
        component: EducationPointDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EducationPoints'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const educationPointPopupRoute: Routes = [
    {
        path: 'education-point-new',
        component: EducationPointPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EducationPoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'education-point/:id/edit',
        component: EducationPointPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EducationPoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'education-point/:id/delete',
        component: EducationPointDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EducationPoints'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
