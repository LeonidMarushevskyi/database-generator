import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { DateUtils } from 'ng-jhipster';

import { BodyOfWater } from './body-of-water.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BodyOfWaterService {

    private resourceUrl = 'api/body-of-waters';

    constructor(private http: Http, private dateUtils: DateUtils) { }

    create(bodyOfWater: BodyOfWater): Observable<BodyOfWater> {
        const copy = this.convert(bodyOfWater);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(bodyOfWater: BodyOfWater): Observable<BodyOfWater> {
        const copy = this.convert(bodyOfWater);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<BodyOfWater> {
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

    private convert(bodyOfWater: BodyOfWater): BodyOfWater {
        const copy: BodyOfWater = Object.assign({}, bodyOfWater);

        copy.createDateTime = this.dateUtils.toDate(bodyOfWater.createDateTime);

        copy.updateDateTime = this.dateUtils.toDate(bodyOfWater.updateDateTime);
        return copy;
    }
}
