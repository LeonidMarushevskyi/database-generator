import { Application } from '../application';
export class LicensureHistory {
    constructor(
        public id?: number,
        public licensureHistoryQuestion1?: boolean,
        public licensureHistoryQuestion11?: string,
        public licensureHistoryQuestion12?: string,
        public licensureHistoryQuestion2?: boolean,
        public licensureHistoryQuestion21?: string,
        public licensureHistoryQuestion3?: boolean,
        public licensureHistoryQuestion31?: string,
        public licensureHistoryQuestion4?: boolean,
        public licensureHistoryQuestion41?: string,
        public licensureHistoryQuestion5?: boolean,
        public licensureHistoryQuestion51?: string,
        public licensureHistoryQuestion6?: boolean,
        public licensureHistoryQuestion61?: string,
        public licensureHistoryQuestion7?: boolean,
        public licensureHistoryQuestion71?: string,
        public application?: Application,
    ) {
        this.licensureHistoryQuestion1 = false;
        this.licensureHistoryQuestion2 = false;
        this.licensureHistoryQuestion3 = false;
        this.licensureHistoryQuestion4 = false;
        this.licensureHistoryQuestion5 = false;
        this.licensureHistoryQuestion6 = false;
        this.licensureHistoryQuestion7 = false;
    }
}
