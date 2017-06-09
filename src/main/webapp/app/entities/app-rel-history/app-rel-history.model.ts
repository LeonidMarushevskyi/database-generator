import { RelationshipEvent } from '../relationship-event';
import { RelationshipType } from '../relationship-type';
import { Applicant } from '../applicant';
export class AppRelHistory {
    constructor(
        public id?: number,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public startEvent?: RelationshipEvent,
        public endEvent?: RelationshipEvent,
        public relationshipType?: RelationshipType,
        public applicant?: Applicant,
    ) {
    }
}
