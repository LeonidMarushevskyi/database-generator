import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    CountyTypeService,
    CountyTypePopupService,
    CountyTypeComponent,
    CountyTypeDetailComponent,
    CountyTypeDialogComponent,
    CountyTypePopupComponent,
    CountyTypeDeletePopupComponent,
    CountyTypeDeleteDialogComponent,
    countyTypeRoute,
    countyTypePopupRoute,
} from './';

const ENTITY_STATES = [
    ...countyTypeRoute,
    ...countyTypePopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CountyTypeComponent,
        CountyTypeDetailComponent,
        CountyTypeDialogComponent,
        CountyTypeDeleteDialogComponent,
        CountyTypePopupComponent,
        CountyTypeDeletePopupComponent,
    ],
    entryComponents: [
        CountyTypeComponent,
        CountyTypeDialogComponent,
        CountyTypePopupComponent,
        CountyTypeDeleteDialogComponent,
        CountyTypeDeletePopupComponent,
    ],
    providers: [
        CountyTypeService,
        CountyTypePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorCountyTypeModule {}
