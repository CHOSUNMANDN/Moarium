package back.springbootdeveloper.seungchan.entity;


import back.springbootdeveloper.seungchan.util.DayUtill;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_info")
public class UserInfo implements UserDetails {
    @Id // id 필드를 기본키로 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 기본키 자동으로 1씩 증가
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "name", length = 10, nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String name;

    @Column(name = "phone_num", length = 20, nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String phoneNum;

    @Column(name = "major", length = 20, nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String major;

    @Column(name = "gpa", length = 10, nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String gpa;

    @Column(name = "address", length = 100, nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String address;

    @Column(name = "specialty_skill", length = 255, nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String specialtySkill;

    @Column(name = "hobby", length = 255, nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String hobby;

    @Column(name = "mbti", length = 10, nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String mbti;

    @Column(name = "student_id", length = 10, nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String studentId;

    @Column(name = "birth_date", length = 20, nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String birthDate;

    @Column(name = "advantages", length = 1000, nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String advantages;

    @Column(name = "disadvantage", length = 1000, nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String disadvantage;

    @Column(name = "self_introduction", length = 1000, nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String selfIntroduction;

    @Column(name = "photo", length = 255, nullable = false) // 'title'이라는 not null 컴럼과 매핑
    private String photo;

    @Column(name = "ob", nullable = false)
    private boolean isOb;

    @Column(name = "year_registration", length = 20, nullable = false)
    private String yearOfRegistration;

    @Column(name = "email", length = 40, nullable = false, unique = true)
    private String email;

    @Column(name = "password", length = 100, nullable = false)
    private String password;

    @Column(name = "regular_member", nullable = false)
    private boolean regularMember;

    @Builder
    public UserInfo(String name, String phoneNum, String major, String gpa, String address, String specialtySkill, String hobby, String mbti, String studentId, String birthDate, String advantages, String disadvantage, String selfIntroduction, String photo, boolean isOb, String email, String password) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.major = major;
        this.gpa = gpa;
        this.address = address;
        this.specialtySkill = specialtySkill;
        this.hobby = hobby;
        this.mbti = mbti;
        this.studentId = studentId;
        this.birthDate = birthDate;
        this.advantages = advantages;
        this.disadvantage = disadvantage;
        this.selfIntroduction = selfIntroduction;
        this.photo = photo;
        this.isOb = isOb;
        this.yearOfRegistration = DayUtill.getYear();
        this.email = email;
        this.password = password;
        this.regularMember = true;
    }

    @Builder
    public UserInfo(String name, String phoneNum, String major, String gpa, String address, String specialtySkill, String hobby, String mbti, String studentId, String birthDate, String advantages, String disadvantage, String selfIntroduction, String photo, String email, String password) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.major = major;
        this.gpa = gpa;
        this.address = address;
        this.specialtySkill = specialtySkill;
        this.hobby = hobby;
        this.mbti = mbti;
        this.studentId = studentId;
        this.birthDate = birthDate;
        this.advantages = advantages;
        this.disadvantage = disadvantage;
        this.selfIntroduction = selfIntroduction;
        this.photo = photo;
        this.yearOfRegistration = DayUtill.getYear();
        this.email = email;
        this.password = password;
        this.regularMember = true;
    }

    @Builder
    public UserInfo(String name, String phoneNum, String major, String gpa, String address, String specialtySkill, String hobby, String mbti, String studentId, String birthDate, String advantages, String disadvantage, String selfIntroduction, String photo) {
        this.name = name;
        this.phoneNum = phoneNum;
        this.major = major;
        this.gpa = gpa;
        this.address = address;
        this.specialtySkill = specialtySkill;
        this.hobby = hobby;
        this.mbti = mbti;
        this.studentId = studentId;
        this.birthDate = birthDate;
        this.advantages = advantages;
        this.disadvantage = disadvantage;
        this.selfIntroduction = selfIntroduction;
        this.photo = photo;
        this.yearOfRegistration = DayUtill.getYear();
        this.regularMember = true;
    }
    public void update(UserInfo user) {
        this.id = user.getId();
        this.name = user.getName();
        this.phoneNum = user.getPhoneNum();
        this.major = user.getMajor();
        this.gpa = user.getGpa();
        this.address = user.getAddress();
        this.specialtySkill = user.getSpecialtySkill();
        this.hobby = user.getHobby();
        this.mbti = user.getMbti();
        this.studentId = user.getStudentId();
        this.birthDate = user.getBirthDate();
        this.advantages = user.getAdvantages();
        this.disadvantage = user.getDisadvantage();
        this.selfIntroduction = user.getSelfIntroduction();
        this.photo = user.getPhoto();
    }

    // 권한 관련 ---------------------------------------a

    // 사용자가 가지고 있는 권한의 목록을 반환합니다.
    // 현재 예제 코드에서는 사용자 이외의 권한이 없기 때문에 user 권한만 담아 반환한다.
    @Override // 권한 반환
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // 사용자 이외의 권한이 없기 때문에 user 유저 권한만 담아 반환
        return List.of(new SimpleGrantedAuthority("user"));
    }

    // 사용자를 식별할 수 있는 사용자 이름을 반환합니다.
    // 이때 사용되는 사용자 이름은 반드시 고유해야 합니다.
    // 현재 예제 코드는 유니크 속성이 적용된 이메일을 반환합니다
    @Override // 사용자의 id를 반환 (고유한 값)
    public String getUsername() {
        return email;
    }

    // 사용자의 비밀번호를 반환합니다.
    // 이때 저장되어 있는 비밀번호는 암호화해서 저장한다.
    @Override // 사용자의 패스워드를 반환
    public String getPassword() {
        return password;
    }

    // 계정이 만료되었는지 확인하는 메서드입니다.
    // 만약 만료되지 않은때는
    @Override // 계정 만료 여부 반환
    public boolean isAccountNonExpired() {
        // 만료되었는지 확인하는 로직
        return true;
    }

    // 계정이 잠금되었는지 확인하는 메서드입니다.
    // 만약 잠금되지 않은 때는 true를 반환합니다.
    @Override // 계정 잠금 여부 반환
    public boolean isAccountNonLocked() {
        // 계정 잠금되었는지 확인하는 로직
        return true; // true -> 잠금되지 않았음
    }

    // 비밀번호가 만료되었는지 확인하는 메서드입니다.
    // 만약 만료되지 않은 때는 true를 반환합니다.
    @Override // 패스워드의 만료 여부 반환
    public boolean isCredentialsNonExpired() {
        // 패스워드가 만료되었는지 확인하는 로직
        return true; // ture -> 만료되지 않았음
    }

    // 계정이 사용 가능한지 확인하는 메서드입니다.
    // 만약 사용 가능하다면 true을 반환한다.
    @Override // 계정 사용 가능 여부 반환
    public boolean isEnabled() {
        // 계정이 사용 가능한지 확인하는 로직
        return true; // true -> 사용 가능
    }
}

