import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { PersonPreviousName } from './person-previous-name.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PersonPreviousNameService {

    private resourceUrl = 'api/person-previous-names';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(personPreviousName: PersonPreviousName): Observable<PersonPreviousName> {
        const copy = this.convert(personPreviousName);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(personPreviousName: PersonPreviousName): Observable<PersonPreviousName> {
        const copy = this.convert(personPreviousName);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<PersonPreviousName> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convertItemFromServer(entity: any) {
        entity.createDateTime = this.dateUtils
            .convertDateTimeFromServer(entity.createDateTime);
        entity.updateDateTime = this.dateUtils
            .convertDateTimeFromServer(entity.updateDateTime);
    }

    private convert(personPreviousName: PersonPreviousName): PersonPreviousName {
        const copy: PersonPreviousName = Object.assign({}, personPreviousName);

        copy.createDateTime = this.dateUtils.toDate(personPreviousName.createDateTime);

        copy.updateDateTime = this.dateUtils.toDate(personPreviousName.updateDateTime);
        return copy;
    }
}
