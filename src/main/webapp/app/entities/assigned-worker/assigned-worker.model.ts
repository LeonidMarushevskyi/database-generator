import { Person } from '../person';
export class AssignedWorker {
    constructor(
        public id?: number,
        public code?: string,
        public person?: Person,
    ) {
    }
}
