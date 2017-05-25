export class Facility {
    constructor(
        public id?: number,
        public name?: string,
        public licenseeName?: string,
        public licenseeType?: string,
        public licenseNumber?: number,
        public licenseStatus?: string,
        public capacity?: number,
        public licenseEffectiveDate?: any,
        public originalApplicationRecievedDate?: any,
        public lastVisitDate?: any,
        public emailAddress?: string,
        public lastVisitReason?: string,
        public addressId?: number,
        public phoneId?: number,
        public childId?: number,
        public complaintId?: number,
        public inspectionId?: number,
        public assignedWorkerId?: number,
        public districtOfficeId?: number,
        public typeId?: number,
        public statusId?: number,
        public countyId?: number,
    ) {
    }
}
