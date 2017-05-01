export class Facility {
    constructor(
        public id?: number,
        public type?: string,
        public name?: string,
        public licenseeName?: string,
        public licenseeType?: string,
        public assignedWorker?: string,
        public districtOffice?: string,
        public licenseNumber?: number,
        public licenseStatus?: string,
        public capacity?: number,
        public licenseEffectiveDate?: any,
        public originalApplicationRecievedDate?: any,
        public lastVisitDate?: any,
        public emailAddress?: string,
        public lastVisitReason?: string,
        public county?: string,
        public addressId?: number,
        public phoneId?: number,
        public childId?: number,
    ) {
    }
}
