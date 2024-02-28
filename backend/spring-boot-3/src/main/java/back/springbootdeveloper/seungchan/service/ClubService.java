package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubService {
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;

    /**
     * 클럽 멤버의 ID를 기반으로 해당 멤버가 속한 클럽의 이름을 반환합니다.
     *
     * @param clubMemberId 클럽 멤버의 고유 식별자
     * @return 클럽 이름
     * @throws EntityNotFoundException 지정된 멤버 또는 클럽을 찾을 수 없을 때 발생하는 예외
     */
    public String getClubName(Long clubMemberId) {
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId).orElseThrow(EntityNotFoundException::new);
        Club club = clubRepository.findById(clubMember.getClubId()).orElseThrow(EntityNotFoundException::new);

        return club.getClubName();
    }
}
