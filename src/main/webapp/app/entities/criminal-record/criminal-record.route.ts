import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { CriminalRecordComponent } from './criminal-record.component';
import { CriminalRecordDetailComponent } from './criminal-record-detail.component';
import { CriminalRecordPopupComponent } from './criminal-record-dialog.component';
import { CriminalRecordDeletePopupComponent } from './criminal-record-delete-dialog.component';

import { Principal } from '../../shared';

export const criminalRecordRoute: Routes = [
    {
        path: 'criminal-record',
        component: CriminalRecordComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CriminalRecords'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'criminal-record/:id',
        component: CriminalRecordDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CriminalRecords'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const criminalRecordPopupRoute: Routes = [
    {
        path: 'criminal-record-new',
        component: CriminalRecordPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CriminalRecords'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'criminal-record/:id/edit',
        component: CriminalRecordPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CriminalRecords'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'criminal-record/:id/delete',
        component: CriminalRecordDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'CriminalRecords'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
