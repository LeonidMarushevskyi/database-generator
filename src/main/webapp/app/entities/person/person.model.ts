import { Application } from '../application';
import { Household } from '../household';
import { EducationLevelType } from '../education-level-type';
import { PersonPreviousName } from '../person-previous-name';
import { EmailAddress } from '../email-address';
import { PersonPhone } from '../person-phone';
import { Employment } from '../employment';
import { PersonAddress } from '../person-address';
import { EducationPoint } from '../education-point';
import { GenderType } from '../gender-type';
import { RaceType } from '../race-type';
import { EthnicityType } from '../ethnicity-type';
export class Person {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public middleName?: string,
        public ssn?: string,
        public dateOfBirth?: any,
        public driversLicenseNumber?: string,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public application?: Application,
        public household?: Household,
        public educationHighestLevel?: EducationLevelType,
        public previousNames?: PersonPreviousName,
        public emailAddresses?: EmailAddress,
        public phoneNumbers?: PersonPhone,
        public employments?: Employment,
        public addresses?: PersonAddress,
        public educationPoints?: EducationPoint,
        public gender?: GenderType,
        public race?: RaceType,
        public ethnicities?: EthnicityType,
    ) {
    }
}
