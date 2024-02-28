package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.response.ImageResDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class ImageService {

    private final String imageDir;

    // ImageService의 코드를 살펴보자. 먼저, 저장 경로를 뜻하는 imageDir 을 @Value 어노테이션을 통해 주입받고 있는데, 이유는 다음과 같다.
    // - 정적 이미지가 어디에 존재하는지 감추고 싶다.
    // - 로컬에서 구동할때와 ec2 인스턴스에서 구동할 때의 저장 경로를 달리하려는데, 매번 저장 경로 코드를 바꾸기가 번거롭다.
    // 따라서 imageDir 은 application.yml 에서 주입하는 방식을 사용했다.
    public ImageService(@Value("${image-dir}") String imageDir) {
        this.imageDir = imageDir;
    }

    public ImageResDto uploadImage(MultipartFile image) {
        // 다음으로, 이미지의 이름을 지정해주어야 한다. 이미지 이름을 저장하는 방식은 {UUID}.{extension} 으로 정했는데,
        // 클라이언트가 이미지를 요청하는 방식이 {server_url}/path/{UUID}.{extension} 이기 때문에 server url 까지 같이 저장해줄까? 하는 생각을 했었다.
        // 하지만, 조금 더 생각해보고 다음과 같은 이유로 생각을 바꾸었다.
        // - 만약, 서버의 도메인 네임이 수정된다고 하면, DB에 이미 들어가있는 이미지 이름 데이터들을 다 수정할 것인가? 변경에 자유롭지 못해진다.
        // - 도메인이 변경되거나, 도메인 변경과 함께 이미지 서버 자체가 통째로 변경된다 해도,
        //      이미지 파일들만 잘 마이그레이션 한다면 프론트 측에서 이미지 리소스를 받을 때 prefix에 도메인 네임만 변경함으로써 쉽게 변경이 가능하다.
        final String extension = image.getContentType().split("/")[1];
        final String imageName = UUID.randomUUID() + "." + extension;

        try {
            final File file = new File(imageDir + imageName);
            // 저장 디렉토리와 이미지 네임을 합친 경로로 File 객체를 생성해준다.
            // 다음으로 요청으로 받은 이미지 데이터를 경로에 저장하면 되는데,
            // 스프링에서는 아주 편리하게도 MultipartFile 객체에서 transferTo 메서드로 이를 해결해준다.
            // MultipartFile 객체가 transferTo 메서드의 파라미터로 Path 객체를 넘겨주면 (File 객체를 넘겨도 된다) 해당  경로로 파일을 잘 복사해준다.
            // 이렇게 간단히 지정한 경로로 이미지 파일을 저장해줄 수 있다.
            image.transferTo(file);
        } catch (Exception e) {
            throw new NullPointerException();
        }

        return new ImageResDto(imageName);
    }
}