package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.response.ImageResDto;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class ImageService {

  // TODO: 3/5/24 배포시 변경
  @Value("${dev-base-url}")
  private String imageBaseDirUrl;
  @Value("${club-base-url}")
  private String clubImageBaseDirUrl;
  @Value("${club-profile}")
  private String clubProfileDirUrl;
  @Value("${club-information}")
  private String clubInformationDirUrl;
  @Value("${base-image-url}")
  private String baseImageDirUrl;
  @Value("${base-image-name}")
  private String baseImageName;

  public String getBaseImageUrl() {
    return imageBaseDirUrl + baseImageDirUrl + baseImageName;
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
      final File file = new File(imageBaseDirUrl + imageName);
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

  /**
   * 클럽 프로필 이미지를 업로드하고 이미지 URL을 반환합니다.
   *
   * @param clubName             클럽 이름
   * @param clubProfileImageFile 클럽 프로필 이미지 파일
   * @return 업로드된 이미지의 URL
   */
  public String uploadClubProfileImage(final String clubName,
      final MultipartFile clubProfileImageFile) {
    return uploadImageAndGetUrl(clubImageBaseDirUrl, clubProfileDirUrl, clubProfileImageFile,
        clubName);
  }

  /**
   * 클럽 소개 이미지 파일을 업로드하고 이미지 URL 목록을 반환합니다.
   *
   * @param clubName                클럽 이름
   * @param clubIntroduceImageFiles 클럽 소개 이미지 파일의 리스트
   * @return 업로드된 이미지의 URL 목록
   */
  public List<String> uploadClubIntroduceImageUrls(
      final String clubName, final List<MultipartFile> clubIntroduceImageFiles) {
    List<String> imageUrls = new ArrayList<>();

    for (final MultipartFile clubIntroduceImageFile : clubIntroduceImageFiles) {
      imageUrls.add(
          uploadImageAndGetUrl(clubImageBaseDirUrl, clubInformationDirUrl, clubIntroduceImageFile,
              clubName));
    }

    return imageUrls;
  }

  /**
   * 이미지를 업로드하고 URL을 반환합니다.
   *
   * @param imageFirstType  이미지의 첫 번째 유형
   * @param imageSecondType 이미지의 두 번째 유형
   * @param image           업로드할 이미지 파일
   * @param addImageName    추가 이미지 이름
   * @return 업로드된 이미지의 URL
   */
  private String uploadImageAndGetUrl(String imageFirstType, String imageSecondType,
      MultipartFile image, String addImageName) {
    // 다음으로, 이미지의 이름을 지정해주어야 한다. 이미지 이름을 저장하는 방식은 {UUID}.{extension} 으로 정했는데,
    // 클라이언트가 이미지를 요청하는 방식이 {server_url}/path/{UUID}.{extension} 이기 때문에 server url 까지 같이 저장해줄까? 하는 생각을 했었다.
    // 하지만, 조금 더 생각해보고 다음과 같은 이유로 생각을 바꾸었다.
    // - 만약, 서버의 도메인 네임이 수정된다고 하면, DB에 이미 들어가있는 이미지 이름 데이터들을 다 수정할 것인가? 변경에 자유롭지 못해진다.
    // - 도메인이 변경되거나, 도메인 변경과 함께 이미지 서버 자체가 통째로 변경된다 해도,
    //      이미지 파일들만 잘 마이그레이션 한다면 프론트 측에서 이미지 리소스를 받을 때 prefix에 도메인 네임만 변경함으로써 쉽게 변경이 가능하다.
    final String extension = image.getContentType().split("/")[1];
    final String imageName = UUID.randomUUID() + "." + extension;
    final String imageUrl =
        imageBaseDirUrl + imageFirstType + imageSecondType + addImageName + "_" + imageName;
    try {
      final File file = new File(imageUrl);
      // 저장 디렉토리와 이미지 네임을 합친 경로로 File 객체를 생성해준다.
      // 다음으로 요청으로 받은 이미지 데이터를 경로에 저장하면 되는데,
      // 스프링에서는 아주 편리하게도 MultipartFile 객체에서 transferTo 메서드로 이를 해결해준다.
      // MultipartFile 객체가 transferTo 메서드의 파라미터로 Path 객체를 넘겨주면 (File 객체를 넘겨도 된다) 해당  경로로 파일을 잘 복사해준다.
      // 이렇게 간단히 지정한 경로로 이미지 파일을 저장해줄 수 있다.
      image.transferTo(file);
    } catch (Exception e) {
      throw new NullPointerException();
    }

    return imageUrl;
  }
}