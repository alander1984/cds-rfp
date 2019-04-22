package tech.lmru.cdsrfp.config.rest;

import org.springframework.http.HttpStatus;
import org.springframework.web.client.DefaultResponseErrorHandler;

/**
 * Created by Ilya on 21.04.2019.
 */
public class CustomErrorHandler extends DefaultResponseErrorHandler {

    @Override
    protected boolean hasError(int unknownStatusCode) {
        HttpStatus.Series series = HttpStatus.Series.resolve(unknownStatusCode);
        return series == HttpStatus.Series.SERVER_ERROR;
    }
}
