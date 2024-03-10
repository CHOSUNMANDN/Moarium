package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubControl;
import back.springbootdeveloper.seungchan.repository.*;
import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class EntityService {
    private final ClubRepository clubRepository;

    @Autowired
    public EntityService(ClubRepository clubRepository) {
        this.clubRepository = clubRepository;
    }

    /**
     * Club ENTITY 생성 메서드
     */
    @Transactional
    public void applyClub(){
        final String clubClubName = "누리고시원";
        final String clubIntroduce = "누리고시원 자기소개";
        final String club_profile_image = "누리고시원_이미지_URL";
        final String club_introduce_image = "누리고시원_이미지_URL";

        Club club = Club.builder()
                .clubName(clubClubName)
                .clubIntroduce(clubIntroduce)
                .clubProfileImage(club_profile_image)
                .build();
        Club entityClub = clubRepository.save(club);
    }
}
