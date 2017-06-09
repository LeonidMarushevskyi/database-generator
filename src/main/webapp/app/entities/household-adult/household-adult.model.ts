import { Person } from '../person';
import { CriminalRecord } from '../criminal-record';
import { Household } from '../household';
import { StateType } from '../state-type';
export class HouseholdAdult {
    constructor(
        public id?: number,
        public outOfStateDisclosureState?: boolean,
        public criminalRecordStatementQuestion1?: boolean,
        public criminalRecordStatementQuestion2?: boolean,
        public criminalRecordStatementQuestion3?: boolean,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public person?: Person,
        public criminalRecords?: CriminalRecord,
        public household?: Household,
        public otherStates?: StateType,
    ) {
        this.outOfStateDisclosureState = false;
        this.criminalRecordStatementQuestion1 = false;
        this.criminalRecordStatementQuestion2 = false;
        this.criminalRecordStatementQuestion3 = false;
    }
}
