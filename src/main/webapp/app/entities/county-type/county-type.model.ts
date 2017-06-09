import { StateType } from '../state-type';
export class CountyType {
    constructor(
        public id?: number,
        public fipsCode?: string,
        public description?: string,
        public state?: StateType,
    ) {
    }
}
