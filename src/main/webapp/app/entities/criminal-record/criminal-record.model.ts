import { StateType } from '../state-type';
import { HouseholdAdult } from '../household-adult';
export class CriminalRecord {
    constructor(
        public id?: number,
        public offenseDescription?: any,
        public offenseDate?: any,
        public offenseExplanation?: any,
        public city?: string,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public state?: StateType,
        public person?: HouseholdAdult,
    ) {
    }
}
