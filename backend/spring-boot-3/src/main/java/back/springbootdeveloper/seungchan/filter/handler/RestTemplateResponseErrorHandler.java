package back.springbootdeveloper.seungchan.filter.handler;

import back.springbootdeveloper.seungchan.filter.exception.external.DefaultExternalApiClientErrorException;
import back.springbootdeveloper.seungchan.filter.exception.external.DefaultExternalApiServerErrorException;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    // 4xx, 5xx 에러인 경우에만 오류 발생
    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return response.getStatusCode().is4xxClientError() ||
                response.getStatusCode().is5xxServerError();
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if(response.getStatusCode().is4xxClientError()){
            System.out.println("Error Body" + response.getBody());
            throw new DefaultExternalApiClientErrorException();
        }else if(response.getStatusCode().is5xxServerError()){
            System.out.println(response.getBody().toString());
            throw new DefaultExternalApiServerErrorException();
        }
    }
}
