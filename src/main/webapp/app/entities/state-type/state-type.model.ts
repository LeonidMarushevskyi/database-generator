import { CountyType } from '../county-type';
export class StateType {
    constructor(
        public id?: number,
        public name?: string,
        public counties?: CountyType,
    ) {
    }
}
