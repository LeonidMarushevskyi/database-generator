import { RelationshipEventType } from '../relationship-event-type';
import { AppRelHistory } from '../app-rel-history';
export class RelationshipEvent {
    constructor(
        public id?: number,
        public eventPlace?: string,
        public eventDate?: any,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public eventType?: RelationshipEventType,
        public relationShip?: AppRelHistory,
    ) {
    }
}
