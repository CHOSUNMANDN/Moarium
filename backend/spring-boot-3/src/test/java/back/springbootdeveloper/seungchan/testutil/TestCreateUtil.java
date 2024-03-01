package back.springbootdeveloper.seungchan.testutil;

import back.springbootdeveloper.seungchan.controller.config.jwt.JwtFactory;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TestCreateUtil {
    @Autowired
    private JwtFactory jwtFactory;
    @Autowired
    private MemberRepository memberRepository;
    private Long ONE_CLUB_LEADER_ID_MEMBER = 1L;
    private Long ONE_CLUB_DEPUTY_LEADER_ID_MEMBER = 2L;
    private Long TWO_CLUB_LEADER_ID_MEMBER = 6L;
    private Long TWO_CLUB_DEPUTY_LEADER_ID_MEMBER = 7L;
    private Long ONE_CLUB_ID = 1L;
    private Long TWO_CLUB_ID = 2L;

    public Long getONE_CLUB_ID() {
        return ONE_CLUB_ID;
    }

    public Long getTWO_CLUB_ID() {
        return TWO_CLUB_ID;
    }

    public Long getONE_CLUB_LEADER_ID_MEMBER() {
        return ONE_CLUB_LEADER_ID_MEMBER;
    }

    public Long getONE_CLUB_DEPUTY_LEADER_ID_MEMBER() {
        return ONE_CLUB_DEPUTY_LEADER_ID_MEMBER;
    }

    public Long getTWO_CLUB_LEADER_ID_MEMBER() {
        return TWO_CLUB_LEADER_ID_MEMBER;
    }

    public Long getTWO_CLUB_DEPUTY_LEADER_ID_MEMBER() {
        return TWO_CLUB_DEPUTY_LEADER_ID_MEMBER;
    }

    /**
     * 1_클럽에 속한 단일 멤버(리더)를 대상으로 토큰을 생성합니다.
     *
     * @return 생성된 토큰
     */
    public String create_token_one_club_leader_member() {
        return jwtFactory.createToken(ONE_CLUB_LEADER_ID_MEMBER);
    }

    /**
     * 1_클럽에 속한 단일 멤버(부 리더)를 대상으로 토큰을 생성합니다.
     *
     * @return 생성된 토큰
     */
    public String create_token_one_club_deputy_leader_member() {
        return jwtFactory.createToken(ONE_CLUB_DEPUTY_LEADER_ID_MEMBER);
    }

    /**
     * 2_클럽에 속한 단일 멤버(리더)를 대상으로 토큰을 생성합니다.
     *
     * @return 생성된 토큰
     */
    public String create_token_two_club_leader_member() {
        return jwtFactory.createToken(TWO_CLUB_LEADER_ID_MEMBER);
    }

    /**
     * 2_클럽에 속한 단일 멤버(부 리더)를 대상으로 토큰을 생성합니다.
     *
     * @return 생성된 토큰
     */
    public String create_token_two_club_deputy_leader_member() {
        return jwtFactory.createToken(TWO_CLUB_DEPUTY_LEADER_ID_MEMBER);
    }

    /**
     * 1_클럽에 속한 단일 멤버(리더)의 엔티티를 반환합니다.
     *
     * @return 클럽에 속한 단일 멤버(리더)의 엔티티
     */
    public Member get_entity_one_club_leader_member() {
        return memberRepository.findById(ONE_CLUB_LEADER_ID_MEMBER).get();
    }

    /**
     * 1_클럽에 속한 단일 부회장 멤버의 엔티티를 반환합니다.
     *
     * @return 클럽에 속한 단일 부회장 멤버의 엔티티
     */
    public Member get_entity_one_club_deputy_leader_member() {
        return memberRepository.findById(ONE_CLUB_DEPUTY_LEADER_ID_MEMBER).get();
    }

    /**
     * 2_클럽에 속한 단일 멤버(리더)의 엔티티를 반환합니다.
     *
     * @return 클럽에 속한 단일 멤버(리더)의 엔티티
     */
    public Member get_entity_two_club_leader_member() {
        return memberRepository.findById(TWO_CLUB_LEADER_ID_MEMBER).get();
    }

    /**
     * 2_클럽에 속한 단일 부회장 멤버의 엔티티를 반환합니다.
     *
     * @return 클럽에 속한 단일 부회장 멤버의 엔티티
     */
    public Member get_entity_two_club_deputy_leader_member() {
        return memberRepository.findById(TWO_CLUB_DEPUTY_LEADER_ID_MEMBER).get();
    }

}
