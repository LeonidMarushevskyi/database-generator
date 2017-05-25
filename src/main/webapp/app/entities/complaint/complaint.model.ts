export class Complaint {
    constructor(
        public id?: number,
        public complaintDate?: any,
        public controlNumber?: string,
        public priorityLevel?: number,
        public status?: string,
        public approvalDate?: any,
        public facilityId?: number,
    ) {
    }
}
