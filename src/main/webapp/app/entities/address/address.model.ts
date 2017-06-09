export class Address {
    constructor(
        public id?: number,
        public streetAddress?: string,
        public city?: string,
        public state?: string,
        public zipCode?: string,
        public zipSuffixCode?: string,
        public longitude?: number,
        public latitude?: number,
        public deliverable?: boolean,
        public createUserId?: string,
        public createDateTime?: any,
        public updateUserId?: string,
        public updateDateTime?: any,
    ) {
        this.deliverable = false;
    }
}
