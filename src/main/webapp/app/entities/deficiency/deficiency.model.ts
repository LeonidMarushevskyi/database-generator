export class Deficiency {
    constructor(
        public id?: number,
        public deficiencyType?: string,
        public deficiencyTypeDescription?: string,
        public pocDate?: any,
        public facSectionViolated?: string,
        public deficiency?: string,
        public correctionPlan?: string,
        public inspectionId?: number,
    ) {
    }
}
