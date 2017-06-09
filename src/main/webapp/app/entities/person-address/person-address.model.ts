import { Person } from '../person';
import { AddressType } from '../address-type';
import { Address } from '../address';
export class PersonAddress {
    constructor(
        public id?: number,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public person?: Person,
        public addressType?: AddressType,
        public address?: Address,
    ) {
    }
}
