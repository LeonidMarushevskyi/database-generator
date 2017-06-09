import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { EducationLevelTypeComponent } from './education-level-type.component';
import { EducationLevelTypeDetailComponent } from './education-level-type-detail.component';
import { EducationLevelTypePopupComponent } from './education-level-type-dialog.component';
import { EducationLevelTypeDeletePopupComponent } from './education-level-type-delete-dialog.component';

import { Principal } from '../../shared';

export const educationLevelTypeRoute: Routes = [
    {
        path: 'education-level-type',
        component: EducationLevelTypeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EducationLevelTypes'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'education-level-type/:id',
        component: EducationLevelTypeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EducationLevelTypes'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const educationLevelTypePopupRoute: Routes = [
    {
        path: 'education-level-type-new',
        component: EducationLevelTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EducationLevelTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'education-level-type/:id/edit',
        component: EducationLevelTypePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EducationLevelTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'education-level-type/:id/delete',
        component: EducationLevelTypeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'EducationLevelTypes'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
