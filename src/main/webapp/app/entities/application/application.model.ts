import { LicensureHistory } from '../licensure-history';
import { ChildPreferences } from '../child-preferences';
import { Person } from '../person';
import { DeterminedChild } from '../determined-child';
import { Applicant } from '../applicant';
import { CountyType } from '../county-type';
import { ApplicationStatusType } from '../application-status-type';
export class Application {
    constructor(
        public id?: number,
        public isInitialApplication?: boolean,
        public otherApplicationType?: string,
        public isChildIdentified?: boolean,
        public isChildCurrentlyInYourHome?: boolean,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public licensureHistory?: LicensureHistory,
        public ?: ChildPreferences,
        public references?: Person,
        public determinedChild?: DeterminedChild,
        public applicants?: Applicant,
        public forCountyUseOnly?: CountyType,
        public status?: ApplicationStatusType,
    ) {
        this.isInitialApplication = false;
        this.isChildIdentified = false;
        this.isChildCurrentlyInYourHome = false;
    }
}
