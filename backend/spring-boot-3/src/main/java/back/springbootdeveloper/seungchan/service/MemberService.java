package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    public Member findMemberById(Long id){
        return memberRepository.findById(id)
                .orElseThrow(); // TODO: Exception
    }

    // 로그인 시 토큰 조회를 위한 함수
    // email이 없는 경우에는 새로운 member를 만들어야 하므로 orElse를 null로 처리
    public Member findByEmailForJwtToken(String email){
        return memberRepository.findByEmail(email).orElse(null);
    }

    // TODO: Error Handling
    public Member findByEmail(String email){
        return  memberRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("Email Does Not Exist"));
    }

    public Member createMemberByEmail(String email){
        return memberRepository.save(new Member(email));
    }
    public Member findByMemberId(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
    }
}
