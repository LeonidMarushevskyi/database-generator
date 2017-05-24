export class Address {
    constructor(
        public id?: number,
        public streetAddress?: string,
        public city?: string,
        public state?: string,
        public zipCode?: string,
        public zipSuffixCode?: string,
        public longitude?: number,
        public lattitude?: number,
        public deliverable?: boolean,
    ) {
        this.deliverable = false;
    }
}
