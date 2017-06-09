import { Address } from '../address';
import { BodyOfWater } from '../body-of-water';
import { AddressType } from '../address-type';
import { PosessionType } from '../posession-type';
import { Household } from '../household';
export class HouseholdAddress {
    constructor(
        public id?: number,
        public isWeaponsInHome?: boolean,
        public directionsToHome?: string,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public address?: Household,
        public bodyOfWaters?: BodyOfWater,
        public addressType?: AddressType,
        public posessionType?: PosessionType,
    ) {
        this.isWeaponsInHome = false;
    }
}
