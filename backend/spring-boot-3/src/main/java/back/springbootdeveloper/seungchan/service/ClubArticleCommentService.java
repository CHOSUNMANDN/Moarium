package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.dto.response.ResponseMessage;
import back.springbootdeveloper.seungchan.dto.response.ClubMemberCommentsResDto;
import back.springbootdeveloper.seungchan.dto.response.MyClubArticleComment;
import back.springbootdeveloper.seungchan.entity.ClubArticle;
import back.springbootdeveloper.seungchan.entity.ClubArticleComment;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubArticleCommentRepository;
import back.springbootdeveloper.seungchan.repository.ClubArticleRepository;
import back.springbootdeveloper.seungchan.repository.ClubMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubArticleCommentService {

  private final ClubArticleRepository clubArticleRepository;
  private final ClubArticleCommentRepository clubArticleCommentRepository;
  private final ClubMemberRepository clubMemberRepository;

  /**
   * 클럽 멤버의 아이디를 기반으로 해당 멤버가 작성한 모든 댓글을 검색하여 클럽 멤버 댓글 응답 DTO를 반환합니다.
   *
   * @param clubMemberId 클럽 멤버의 고유 식별자
   * @return 클럽 멤버 댓글 응답 DTO
   */
  public ClubMemberCommentsResDto getClubMemberCommentsResDto(Long clubMemberId) {
    List<ClubArticle> clubArticles = clubArticleRepository.findAllByClubMemberId(clubMemberId);
    List<MyClubArticleComment> myClubArticleComments = new ArrayList<>();

    // 모든 게시물을 순회하면서 각 게시물에 속한 댓글을 가져와서 리스트에 추가합니다.
    for (ClubArticle clubArticle : clubArticles) {
      clubArticle.getClubArticleComments().forEach(clubArticleComment ->
          myClubArticleComments.add(new MyClubArticleComment(clubArticleComment)));
    }

    // 클럽 멤버 댓글 응답 DTO를 생성하여 반환합니다.
    return ClubMemberCommentsResDto.builder()
        .myClubArticleComments(myClubArticleComments)
        .build();
  }

  /**
   * 주어진 회원 ID와 댓글 ID를 기반으로 해당 회원이 댓글의 작성자인지 여부를 확인합니다.
   *
   * @param memberId  회원 ID
   * @param commentId 댓글 ID
   * @return 해당 회원이 댓글의 작성자이면 true, 아니면 false
   * @throws EntityNotFoundException 엔티티를 찾을 수 없을 때 발생하는 예외
   */
  public Boolean isAuthor(Long memberId, Long commentId) {
    final ClubArticleComment clubArticleComment = clubArticleCommentRepository.findById(commentId)
        .orElseThrow(EntityNotFoundException::new);

    return isSame(memberId, clubArticleComment);
  }

  /**
   * 특정 클럽 게시물의 마지막 댓글을 반환합니다.
   *
   * @param clubArticleId 클럽 게시물의 ID입니다.
   * @return 해당 클럽 게시물의 마지막 댓글입니다.
   * @throws EntityNotFoundException 주어진 ID에 해당하는 클럽 게시물을 찾을 수 없을 때 발생합니다.
   */
  public ClubArticleComment getLastClubArticleComment(final Long clubArticleId) {
    ClubArticle clubArticle = clubArticleRepository.findById(clubArticleId)
        .orElseThrow(EntityNotFoundException::new);
    List<ClubArticleComment> clubArticleComments = clubArticle.getClubArticleComments();
    Integer lastIndex = clubArticleComments.size() - 1;

    return clubArticleComments.get(lastIndex);
  }

  /**
   * 주어진 댓글 ID에 해당하는 댓글을 삭제합니다.
   *
   * @param commentId 댓글 ID
   */
  @Transactional
  public void deleteByCommentId(Long commentId) {
    clubArticleCommentRepository.deleteById(commentId);
  }

  /**
   * 주어진 회원 ID와 클럽 게시물 댓글이 동일한지 확인합니다.
   *
   * @param memberId           회원 ID
   * @param clubArticleComment 클럽 게시물 댓글
   * @return 회원 ID와 클럽 게시물 댓글이 동일한지 여부
   */
  private boolean isSame(Long memberId, ClubArticleComment clubArticleComment) {
    return clubArticleComment.getMemberId() == memberId;
  }
}
