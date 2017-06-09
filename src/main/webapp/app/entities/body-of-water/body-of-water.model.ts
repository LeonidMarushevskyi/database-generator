import { HouseholdAddress } from '../household-address';
export class BodyOfWater {
    constructor(
        public id?: number,
        public location?: string,
        public size?: number,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public householdAddress?: HouseholdAddress,
    ) {
    }
}
