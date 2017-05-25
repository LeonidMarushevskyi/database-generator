import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    DistrictOfficeService,
    DistrictOfficePopupService,
    DistrictOfficeComponent,
    DistrictOfficeDetailComponent,
    DistrictOfficeDialogComponent,
    DistrictOfficePopupComponent,
    DistrictOfficeDeletePopupComponent,
    DistrictOfficeDeleteDialogComponent,
    districtOfficeRoute,
    districtOfficePopupRoute,
} from './';

const ENTITY_STATES = [
    ...districtOfficeRoute,
    ...districtOfficePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DistrictOfficeComponent,
        DistrictOfficeDetailComponent,
        DistrictOfficeDialogComponent,
        DistrictOfficeDeleteDialogComponent,
        DistrictOfficePopupComponent,
        DistrictOfficeDeletePopupComponent,
    ],
    entryComponents: [
        DistrictOfficeComponent,
        DistrictOfficeDialogComponent,
        DistrictOfficePopupComponent,
        DistrictOfficeDeleteDialogComponent,
        DistrictOfficeDeletePopupComponent,
    ],
    providers: [
        DistrictOfficeService,
        DistrictOfficePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorDistrictOfficeModule {}
