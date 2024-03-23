package back.springbootdeveloper.seungchan.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import back.springbootdeveloper.seungchan.service.EntityService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EntityServiceTest {

    @Mock
    private ClubRepository clubRepository;

    @InjectMocks
    private EntityService entityService;

    @Test
    public void testApplyClub() {
        clubRepository.deleteAll();

//        entityService.applyClub();

        // Then
//        verify(clubRepository, times(1)).save(any(Club.class));
//        assertThat(clubRepository.count()).

        final String clubClubName = "누리고시원";
        final String clubIntroduce = "누리고시원 자기소개";
        final String club_profile_image = "누리고시원_이미지_URL";

        Club club = Club.builder()
                .clubName(clubClubName)
                .clubIntroduce(clubIntroduce)
                .clubProfileImage(club_profile_image)
                .build();

        Club entityClub = clubRepository.save(club);
    }
}