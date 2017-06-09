import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { RelationshipEvent } from './relationship-event.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class RelationshipEventService {

    private resourceUrl = 'api/relationship-events';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(relationshipEvent: RelationshipEvent): Observable<RelationshipEvent> {
        const copy = this.convert(relationshipEvent);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(relationshipEvent: RelationshipEvent): Observable<RelationshipEvent> {
        const copy = this.convert(relationshipEvent);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<RelationshipEvent> {
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
        entity.eventDate = this.dateUtils
            .convertLocalDateFromServer(entity.eventDate);
        entity.createDateTime = this.dateUtils
            .convertDateTimeFromServer(entity.createDateTime);
        entity.updateDateTime = this.dateUtils
            .convertDateTimeFromServer(entity.updateDateTime);
    }

    private convert(relationshipEvent: RelationshipEvent): RelationshipEvent {
        const copy: RelationshipEvent = Object.assign({}, relationshipEvent);
        copy.eventDate = this.dateUtils
            .convertLocalDateToServer(relationshipEvent.eventDate);

        copy.createDateTime = this.dateUtils.toDate(relationshipEvent.createDateTime);

        copy.updateDateTime = this.dateUtils.toDate(relationshipEvent.updateDateTime);
        return copy;
    }
}
