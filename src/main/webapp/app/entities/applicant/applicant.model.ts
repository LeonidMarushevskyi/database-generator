import { HouseholdAdult } from '../household-adult';
import { AppRelHistory } from '../app-rel-history';
import { Application } from '../application';
export class Applicant {
    constructor(
        public id?: number,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public householdPerson?: HouseholdAdult,
        public relationHistoryRecords?: AppRelHistory,
        public application?: Application,
    ) {
    }
}
