package back.springbootdeveloper.seungchan.repository;

import back.springbootdeveloper.seungchan.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserInfo, Long> {
    @Transactional
    @Modifying
    @Query("UPDATE UserInfo u SET u.name = :#{#user.name}, u.phoneNum = :#{#user.phoneNum}, u.major = :#{#user.major}, u.gpa = :#{#user.gpa}, u.address = :#{#user.address}, u.specialtySkill = :#{#user.specialtySkill}, u.hobby = :#{#user.hobby}, u.mbti = :#{#user.mbti}, u.studentId = :#{#user.studentId}, u.birthDate = :#{#user.birthDate}, u.advantages = :#{#user.advantages}, u.disadvantage = :#{#user.disadvantage}, u.selfIntroduction = :#{#user.selfIntroduction}, u.photo = :#{#user.photo}, u.email = :#{#user.email} WHERE u.userInfoId = :userId")
    void updateUser(Long userId, UserInfo user);

    Optional<UserInfo> findByEmail(String email);

    @Transactional
    @Modifying
    @Query("UPDATE UserInfo u SET  u.userInfoId = :#{#id} WHERE u.userInfoId = :userId")
    void updateId(Long userId, Long id);

    Boolean existsByEmailAndName(String email, String name);

    @Modifying
    @Transactional
    @Query("UPDATE UserInfo u SET u.password = :newPassword WHERE u.email = :email")
    Integer updatePasswordByEmail(@Param("email") String email, @Param("newPassword") String newPassword);

    @Modifying
    @Transactional
    @Query("UPDATE UserInfo u SET u.email = :updateEmail WHERE u.userInfoId = :id")
    Integer updateEmailById(@Param("id") Long id, @Param("updateEmail") String updateEmail);

    boolean existsByNameAndPhoneNum(String name, String phoneNum);

    UserInfo findByNameAndPhoneNum(String name, String phoneNum);
}