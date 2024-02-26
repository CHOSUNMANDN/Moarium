package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.ClubIntroduceImage;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import org.springframework.data.jpa.repository.JpaRepository;

@org.springframework.stereotype.Repository
public interface ClubIntroduceImageRepository extends JpaRepository<ClubIntroduceImage, Long> {

}