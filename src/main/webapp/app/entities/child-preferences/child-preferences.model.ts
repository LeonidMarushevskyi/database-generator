import { AgeGroupType } from '../age-group-type';
import { SiblingGroupType } from '../sibling-group-type';
import { Application } from '../application';
export class ChildPreferences {
    constructor(
        public id?: number,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
        public ageGroupTypes?: AgeGroupType,
        public siblingGroupTypes?: SiblingGroupType,
        public application?: Application,
    ) {
    }
}
