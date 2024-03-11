package back.springbootdeveloper.seungchan.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class FastApiHttpReqService {

  private final WebClient webClient;


  public FastApiHttpReqService(WebClient.Builder webClientBuilder) {
    // baseUrl을 설정하여 기본 URL을 지정
    // 이 URL은 요청 시 사용되는 기본 URL이며, 실제 요청할 때마다 지정된 URI와 합쳐집니다.
    this.webClient = webClientBuilder.baseUrl("http://127.0.0.1:8000/").build();
  }

  // 내부 API를 호출하는 메서드
  public Mono<String> callInternalApi() {
    // GET 요청을 보내고, 응답을 Mono로 받음
    Mono<String> resultMono = webClient.get()
        // 요청할 URI를 지정
        .uri("/fast")
        // 응답을 받아오기 위해 retrieve() 메서드 호출
        .retrieve()
        // 응답 본문을 Mono<String>으로 변환하여 받음
        .bodyToMono(String.class);

    // Mono를 구독하여 비동기적으로 결과를 처리
    return resultMono;
  }
}
