package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.response.ClubMemberCommentsResDto;
import back.springbootdeveloper.seungchan.dto.response.MyClubArticleComment;
import back.springbootdeveloper.seungchan.entity.ClubArticle;
import back.springbootdeveloper.seungchan.entity.ClubMember;
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
}
