import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { PosessionType } from './posession-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PosessionTypeService {

    private resourceUrl = 'api/posession-types';

    constructor(private http: Http) { }

    create(posessionType: PosessionType): Observable<PosessionType> {
        const copy = this.convert(posessionType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(posessionType: PosessionType): Observable<PosessionType> {
        const copy = this.convert(posessionType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<PosessionType> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse);
    }

    private convert(posessionType: PosessionType): PosessionType {
        const copy: PosessionType = Object.assign({}, posessionType);
        return copy;
    }
}
