import { Person } from '../person';
import { RelationshipType } from '../relationship-type';
export class Relationship {
    constructor(
        public id?: number,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public from?: Person,
        public to?: Person,
        public relationshipType?: RelationshipType,
    ) {
    }
}
