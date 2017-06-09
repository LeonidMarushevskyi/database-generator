import { Address } from '../address';
import { PhoneNumber } from '../phone-number';
export class Employer {
    constructor(
        public id?: number,
        public name?: string,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public address?: Address,
        public phoneNumbers?: PhoneNumber,
    ) {
    }
}
