import { Person } from '../person';
import { HouseholdAddress } from '../household-address';
import { LanguageType } from '../language-type';
export class Household {
    constructor(
        public id?: number,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public persons?: Person,
        public addresses?: HouseholdAddress,
        public languages?: LanguageType,
    ) {
    }
}
