import { Person } from '../person';
export class EmailAddress {
    constructor(
        public id?: number,
        public email?: string,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public person?: Person,
    ) {
    }
}
