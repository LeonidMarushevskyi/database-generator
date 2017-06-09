import { EducationLevelType } from '../education-level-type';
import { Address } from '../address';
import { Person } from '../person';
export class EducationPoint {
    constructor(
        public id?: number,
        public degree?: string,
        public name?: string,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public type?: EducationLevelType,
        public educationalInstitutionAddress?: Address,
        public person?: Person,
    ) {
    }
}
