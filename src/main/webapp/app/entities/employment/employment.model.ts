import { Employer } from '../employer';
import { Person } from '../person';
export class Employment {
    constructor(
        public id?: number,
        public occupation?: string,
        public annualIncome?: number,
        public startDate?: any,
        public endDate?: any,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public employer?: Employer,
        public person?: Person,
    ) {
    }
}
