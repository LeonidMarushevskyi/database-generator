import { Person } from '../person';
import { Household } from '../household';
export class HouseholdChild {
    constructor(
        public id?: number,
        public isFinanciallySupported?: boolean,
        public isAdopted?: boolean,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public person?: Person,
        public household?: Household,
    ) {
        this.isFinanciallySupported = false;
        this.isAdopted = false;
    }
}
