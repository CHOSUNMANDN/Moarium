package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.AttendanceNumber;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.AttendanceNumberRepository;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AttendanceNumberService {
    private final ClubRepository clubRepository;
    private final AttendanceNumberRepository attendanceNumberRepository;

    /**
     * 클럽의 출석 번호를 확인합니다.
     *
     * @param clubId          출석 번호를 확인할 클럽의 ID
     * @param numOfAttendance 확인할 출석 번호
     * @return 주어진 출석 번호가 가장 최근의 출석 번호와 동일한지 여부를 나타내는 Boolean 값
     * @throws EntityNotFoundException 지정된 ID에 해당하는 클럽이 없을 때 발생하는 예외
     */
    public Boolean checkAttendanceNumber(Long clubId, String numOfAttendance) {
        Club club = clubRepository.findById(clubId).orElseThrow(EntityNotFoundException::new);
        AttendanceNumber firstAttendanceNumber = getFirstAttendanceNumber(club);

        return isSame(numOfAttendance, firstAttendanceNumber);
    }

    /**
     * 특정 클럽의 마지막 출석 번호를 찾습니다.
     *
     * @param clubId 클럽 식별자
     * @return AttendanceNumber 객체. 만약 해당 클럽에 출석 번호가 없다면 null을 반환합니다.
     * @throws EntityNotFoundException 만약 주어진 clubId에 해당하는 클럽이 존재하지 않을 경우 발생합니다.
     */
    public AttendanceNumber findLastOneByClubId(Long clubId) {
        Club club = clubRepository.findById(clubId).orElseThrow(EntityNotFoundException::new);

        return getFirstAttendanceNumber(club);
    }

    /**
     * 클럽에서 첫 번째 출석 번호를 가져옵니다.
     *
     * @param club 출석 번호를 가져올 클럽
     * @return 첫 번째 출석 번호
     */
    private AttendanceNumber getFirstAttendanceNumber(Club club) {
        List<AttendanceNumber> attendanceNumbers = club.getAttendanceNumbers();
        Integer lastIndex = attendanceNumbers.size() - 1;
        
        return attendanceNumbers.get(lastIndex);
    }

    private boolean isSame(String numOfAttendance, AttendanceNumber firstAttendanceNumber) {
        return firstAttendanceNumber.getAttendanceNumber().equals(numOfAttendance);
    }
}
