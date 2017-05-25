import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { GeneratorSharedModule } from '../../shared';
import {
    CountyService,
    CountyPopupService,
    CountyComponent,
    CountyDetailComponent,
    CountyDialogComponent,
    CountyPopupComponent,
    CountyDeletePopupComponent,
    CountyDeleteDialogComponent,
    countyRoute,
    countyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...countyRoute,
    ...countyPopupRoute,
];

@NgModule({
    imports: [
        GeneratorSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CountyComponent,
        CountyDetailComponent,
        CountyDialogComponent,
        CountyDeleteDialogComponent,
        CountyPopupComponent,
        CountyDeletePopupComponent,
    ],
    entryComponents: [
        CountyComponent,
        CountyDialogComponent,
        CountyPopupComponent,
        CountyDeleteDialogComponent,
        CountyDeletePopupComponent,
    ],
    providers: [
        CountyService,
        CountyPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class GeneratorCountyModule {}
