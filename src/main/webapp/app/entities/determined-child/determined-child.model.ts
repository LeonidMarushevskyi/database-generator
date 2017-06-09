import { Person } from '../person';
import { CountyType } from '../county-type';
import { Application } from '../application';
export class DeterminedChild {
    constructor(
        public id?: number,
        public dateOfPlacement?: any,
        public person?: Person,
        public countyOfJurisdiction?: CountyType,
        public application?: Application,
    ) {
    }
}
