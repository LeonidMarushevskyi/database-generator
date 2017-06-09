import { Employer } from '../employer';
export class PhoneNumber {
    constructor(
        public id?: number,
        public phoneNumber?: string,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public employer?: Employer,
    ) {
    }
}
