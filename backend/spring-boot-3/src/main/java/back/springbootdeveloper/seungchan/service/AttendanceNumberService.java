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
        // attendanceNumbers 목록을 역정렬합니다.
        Collections.sort(club.getAttendanceNumbers(), Comparator.comparing(AttendanceNumber::getCreateDate).reversed());

        // 첫 번째 요소를 가져옵니다.
        AttendanceNumber firstAttendanceNumber = club.getAttendanceNumbers().get(0);

        return isSame(numOfAttendance, firstAttendanceNumber);
    }

    private boolean isSame(String numOfAttendance, AttendanceNumber firstAttendanceNumber) {
        return firstAttendanceNumber.getAttendanceNumber().equals(numOfAttendance);
    }
}
