package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.AttendanceState;
import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.VacationToken;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.AttendanceStateRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.VacationTokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VacationTokenService {
    private final VacationTokenRepository vacationTokenRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final AttendanceStateRepository attendanceStateRepository;

    /**
     * 클럽 회원의 휴가 토큰을 업데이트하고, 변경 여부를 반환합니다.
     *
     * @param clubMemberId        클럽 회원의 ID
     * @param vacationTokenNumber 업데이트할 휴가 토큰의 수
     * @return 휴가 토큰이 변경되었으면 true, 그렇지 않으면 false를 반환합니다.
     */
    @Transactional
    public Boolean updateVacationToken(Long clubMemberId, Integer vacationTokenNumber) {
        ClubMember clubMember = clubMemberRepository.findById(clubMemberId).orElseThrow(EntityNotFoundException::new);
        AttendanceState attendanceState = attendanceStateRepository.findById(clubMember.getAttendanceStateId()).orElseThrow(EntityNotFoundException::new);
        VacationToken vacationToken = attendanceState.getVacationToken();
        Integer beforeVacationToken = vacationToken.getVacationToken();

        if (isNotZero(vacationTokenNumber)) {
            vacationToken.addVacationCount(vacationTokenNumber);
        }

        if (isSame(vacationToken.getVacationToken(), beforeVacationToken)) {
            return false;
        }
        return true;
    }

    /**
     * 해당 메서드는 주어진 휴가 토큰 숫자가 0이 아닌지를 확인합니다.
     *
     * @param vacationTokenNumber 확인할 휴가 토큰 숫자
     * @return 휴가 토큰 숫자가 0이 아니면 {@code true}, 그렇지 않으면 {@code false} 반환
     */
    private boolean isNotZero(Integer vacationTokenNumber) {
        return vacationTokenNumber != 0;
    }


    /**
     * 휴가 토큰이 변경되었는지 여부를 확인합니다.
     *
     * @param afterVacationToken  업데이트 후의 휴가 토큰
     * @param beforeVacationToken 업데이트 전의 휴가 토큰
     * @return 휴가 토큰이 변경되었으면 true, 그렇지 않으면 false를 반환합니다.
     */
    private boolean isSame(Integer afterVacationToken, Integer beforeVacationToken) {
        return afterVacationToken == beforeVacationToken;
    }
}
