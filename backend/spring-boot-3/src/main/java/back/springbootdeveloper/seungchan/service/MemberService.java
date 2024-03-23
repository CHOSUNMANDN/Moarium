package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.ClubMember;
import back.springbootdeveloper.seungchan.entity.Member;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import back.springbootdeveloper.seungchan.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

  private final MemberRepository memberRepository;
  private final ClubMemberRepository clubMemberRepository;

  public Member findByEmail(String email) {
    return memberRepository.findByEmail(email)
        .orElseThrow(EntityNotFoundException::new);
  }

  public Member createMemberByEmail(String email) {
    return memberRepository.save(new Member(email));
  }

  public Member findByMemberId(Long memberId) {
    return memberRepository.findById(memberId).orElseThrow(EntityNotFoundException::new);
  }

  public Member findByClubMemberId(Long clubMemberId) {
    ClubMember clubMember = clubMemberRepository.findById(clubMemberId)
        .orElseThrow(EntityNotFoundException::new);

    return memberRepository.findById(clubMember.getMemberId())
        .orElseThrow(EntityNotFoundException::new);
  }

  public Boolean isSameTargetAndLoginMember(Long loginMemberId, Long targetClubMemberId) {
    ClubMember targetClubMember = clubMemberRepository.findById(targetClubMemberId)
        .orElseThrow(EntityNotFoundException::new);
    Member targetMember = memberRepository.findById(targetClubMember.getMemberId())
        .orElseThrow(EntityNotFoundException::new);

    return targetMember.isSame(loginMemberId);
  }
}
