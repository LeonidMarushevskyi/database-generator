import { Person } from '../person';
export class PersonPreviousName {
    constructor(
        public id?: number,
        public name?: string,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public person?: Person,
    ) {
    }
}
