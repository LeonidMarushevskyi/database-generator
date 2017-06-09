import { PhoneNumber } from '../phone-number';
import { PhoneNumberType } from '../phone-number-type';
import { Person } from '../person';
export class PersonPhone {
    constructor(
        public id?: number,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public phoneNumber?: PhoneNumber,
        public phoneType?: PhoneNumberType,
        public person?: Person,
    ) {
    }
}
