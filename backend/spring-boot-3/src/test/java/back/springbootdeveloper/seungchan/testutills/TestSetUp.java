package back.springbootdeveloper.seungchan.testutills;

import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.repository.*;
import back.springbootdeveloper.seungchan.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;

@Component
public class TestSetUp {
    private final UserService userService;
    private final UserUtillService userUtillService;
    private final AttendanceService attendanceService;
    private final AttendanceTimeService attendanceTimeService;
    private final PeriodicDataService periodicDataService;

    @Autowired
    public TestSetUp(UserService userService, UserUtillService userUtillService, AttendanceService attendanceService, AttendanceTimeService attendanceTimeService, PeriodicDataService periodicDataService) {
        this.userService = userService;
        this.userUtillService = userUtillService;
        this.attendanceService = attendanceService;
        this.attendanceTimeService = attendanceTimeService;
        this.periodicDataService = periodicDataService;
    }

    /**
     * 실장 유저를 설정한다.
     */
    public UserInfo setUpKingUser() {
        // userID 1
        UserInfo user = userService.save(TestMakeObject.makeUser("실장 유저", "seungchan141414@gmail.com"));


        userUtillService.save(TestMakeObject.makeUserUtill(user, 0, true));

        String vacationDates = "2023-08-01, 2023-08-07, 2023-08-14";
        String absenceDates = "2023-08-15";
        String weeklyData = "[0,0,0,0,0]";
        attendanceService.save(TestMakeObject.makeAttendanceStatus(user, vacationDates, absenceDates, weeklyData));

        AttendanceTime attendanceTime = new AttendanceTime(user);
        attendanceTimeService.save(attendanceTime);


        String basicWeeklyData = "[0,0,0,0,0]";
        String basicMonth = "";
        PeriodicData periodicDataOfNewUser = PeriodicData.builder()
                .userId(user.getId())
                .name(user.getName())
                .weeklyData(basicWeeklyData)
                .thisMonth(basicMonth)
                .previousMonth(basicMonth)
                .build();
        periodicDataService.save(periodicDataOfNewUser);

        return user;
    }

    /**
     * 테스트 유저를 위한 설정
     */
    public UserInfo setUpUser() {
        // userID 1
        UserInfo user = userService.save(TestMakeObject.makeUser("일반 유저", "seungchan141414@gmail.com"));

        userUtillService.save(TestMakeObject.makeUserUtill(user, 5, false));

        String vacationDates = "";
        String absenceDates = "";
        String weeklyData = "[0,0,0,0,0]";
        attendanceService.save(TestMakeObject.makeAttendanceStatus(user, vacationDates, absenceDates, weeklyData));

        AttendanceTime attendanceTime = new AttendanceTime(user);
        attendanceTimeService.save(attendanceTime);


        String basicWeeklyData = "[0,0,0,0,0]";
        String basicMonth = "";
        PeriodicData periodicDataOfNewUser = PeriodicData.builder()
                .userId(user.getId())
                .name(user.getName())
                .weeklyData(basicWeeklyData)
                .thisMonth(basicMonth)
                .previousMonth(basicMonth)
                .build();
        periodicDataService.save(periodicDataOfNewUser);

        return user;
    }

    public UserInfo setUpOldUser() {
        // userId OB 2
        UserInfo userOb = userService.save(TestMakeObject.makeUserOb("졸업 유저", "2@gmail.com"));

        return userOb;
    }
}
