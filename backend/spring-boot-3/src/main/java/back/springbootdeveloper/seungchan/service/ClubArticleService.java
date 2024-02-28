package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_CLASSIFICATION;
import back.springbootdeveloper.seungchan.constant.entity.CLUB_ARTICLE_SUGGESTION_CHECK;
import back.springbootdeveloper.seungchan.constant.judgement.AUTHOR_JUDGMENT;
import back.springbootdeveloper.seungchan.dto.request.UpdateClubArticlePutDto;
import back.springbootdeveloper.seungchan.dto.request.WriteSuggestionAnswerReqDto;
import back.springbootdeveloper.seungchan.dto.response.*;
import back.springbootdeveloper.seungchan.entity.*;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubArticleService {
    private final ClubArticleRepository clubArticleRepository;
    private final ClubMemberRepository clubMemberRepository;
    private final ClubRepository clubRepository;
    private final MemberRepository memberRepository;
    private final ClubArticleCommentRepository clubArticleCommentRepository;

    /**
     * 주어진 clubMemberId에 해당하는 클럽 회원의 게시글을 가져와서 해당 회원이 작성한 게시글을 MyClubArticle 객체의 리스트로 변환한 후,
     * ClubMemberArticlesResDto 객체에 설정하여 반환합니다.
     *
     * @param clubMemberId 클럽 회원 ID
     * @return ClubMemberArticlesResDto 객체
     */
    public ClubMemberArticlesResDto getClubMemberArticlesResDto(Long clubMemberId) {
        List<MyClubArticle> myClubArticles = new ArrayList<>();
        List<ClubArticle> clubArticles = clubArticleRepository.findAllByClubMemberId(clubMemberId);

        clubArticles.forEach(clubArticle ->
                myClubArticles.add(new MyClubArticle(clubArticle))
        );

        return ClubMemberArticlesResDto.builder()
                .myClubArticles(myClubArticles)
                .build();
    }

    /**
     * 주어진 회원 ID, 클럽 ID, 게시글 ID를 기준으로 해당 회원이 해당 클럽의 게시글 작성자인지 여부를 확인합니다.
     *
     * @param memberId  회원 ID
     * @param clubId    클럽 ID
     * @param articleId 게시글 ID
     * @return 해당 회원이 해당 클럽의 게시글 작성자이면 true, 아니면 false
     */
    public Boolean isAuthor(Long memberId, Long clubId, Long articleId) {
        final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, memberId).orElseThrow(EntityNotFoundException::new);
        final ClubArticle clubArticle = clubArticleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new);

        return isSame(clubMember, clubArticle);
    }

    /**
     * 주어진 클럽 ID, 회원 ID 및 게시글 ID에 대응하는 클럽 회원과 게시글을 찾은 다음, 주어진 DTO에 있는 정보로 게시글을 업데이트하고 저장합니다.
     *
     * @param clubId                  클럽 ID
     * @param memberId                회원 ID
     * @param articleId               게시글 ID
     * @param updateClubArticlePutDto 업데이트할 게시글의 정보가 담긴 DTO
     * @return 업데이트된 게시글
     * @throws EntityNotFoundException 엔티티를 찾을 수 없을 때 발생하는 예외
     */
    @Transactional
    public ClubArticle updateClubArticle(Long clubId, Long memberId, Long articleId, UpdateClubArticlePutDto updateClubArticlePutDto) {
        final ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, memberId).orElseThrow(EntityNotFoundException::new);
        final ClubArticle clubArticle = clubArticleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new);

        clubArticle.updateTitle(updateClubArticlePutDto.getClubArticleUpdateTitle());
        clubArticle.updateContent(updateClubArticlePutDto.getClubArticleUpdateContent());
        final ClubArticle updateClubArticle = clubArticleRepository.save(clubArticle);

        if (updateClubArticle != null) {
            return updateClubArticle;
        }
        throw new EntityNotFoundException();
    }

    /**
     * 주어진 게시글 ID에 해당하는 클럽 게시글을 삭제합니다.
     *
     * @param clubId    클럽 ID
     * @param memberId  회원 ID
     * @param articleId 게시글 ID
     * @throws EntityNotFoundException 엔티티를 찾을 수 없을 때 발생하는 예외
     */
    @Transactional
    public void deleteClubArticle(Long clubId, Long memberId, Long articleId) {
        final ClubArticle clubArticle = clubArticleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new);
        clubArticleRepository.delete(clubArticle);
    }

    /**
     * 주어진 클럽 ID, 게시글 ID, 회원 ID를 기반으로 클럽 게시글의 상세 정보를 가져옵니다.
     *
     * @param clubId    클럽 ID
     * @param articleId 게시글 ID
     * @param memberId  회원 ID
     * @return 클럽 게시글의 상세 정보를 담은 ClubArticleDetailResDto 객체
     * @throws EntityNotFoundException 엔티티를 찾을 수 없을 때 발생하는 예외
     */
    public ClubArticleDetailResDto getClubArticleDetailResDto(Long clubId, Long articleId, Long memberId) {
        // 게시글 및 회원 정보 조회
        ClubArticle clubArticle = clubArticleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new);
        ClubMember clubMember = clubMemberRepository.findByClubIdAndMemberId(clubId, memberId).orElseThrow(EntityNotFoundException::new);

        // 게시글 댓글 정보 조회
        List<ClubArticleComment> clubArticleComments = clubArticle.getClubArticleComments();
        List<ClubArticleCommentInformation> clubArticleCommentInformations = getClubArticleCommentInformations(memberId, clubArticleComments);
        // 게시글 작성자 여부 확인
        String isClubArticleAuthor = getIsClubArticleAuthor(clubArticle, clubMember);

        // ClubArticleDetailResDto 객체 생성 및 반환
        return ClubArticleDetailResDto.builder()
                .isClubArticleAuthor(isClubArticleAuthor)
                .clubArticleTitle(clubArticle.getTitle())
                .clubArticleContent(clubArticle.getContent())
                .clubArticleLikeNumber(String.valueOf(clubArticle.getLikeCount()))
                .clubArticleCommentNumber(String.valueOf(clubArticle.getClubArticleComments().size()))
                .clubArticleDate(clubArticle.getClubArticleDate())
                .clubArticleAnswerSuggestion(clubArticle.getSuggestionAnswer())
                .clubArticleAnswerCheck(clubArticle.getAnswerCheck().getCheck())
                .clubArticleClassification(clubArticle.getClassification().getSort())
                .clubArticleCommentInformations(clubArticleCommentInformations)
                .build();
    }

    /**
     * 주어진 게시글 ID에 해당하는 클럽 게시글의 좋아요 수를 증가시킵니다.
     *
     * @param articleId 게시글 ID
     * @return 좋아요 수가 증가했는지 여부를 나타내는 값
     * @throws EntityNotFoundException 엔티티를 찾을 수 없을 때 발생하는 예외
     */
    @Transactional
    public Boolean addLikeCountClubArticle(Long articleId) {
        final ClubArticle clubArticle = clubArticleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new);
        Integer clubArticleLikeCount = clubArticle.getLikeCount();
        clubArticle.addLike();

        final ClubArticle updateClubArticle = clubArticleRepository.save(clubArticle);

        return isNotSame(updateClubArticle.getLikeCount(), clubArticleLikeCount);
    }

    /**
     * 주어진 클럽 ID, 회원 ID 및 게시글 분류를 기반으로 해당 클럽 회원의 간단한 정보를 가져옵니다.
     *
     * @param clubId         클럽 ID
     * @param memberId       회원 ID
     * @param classification 게시글 분류
     * @return 해당 클럽 회원의 간단한 정보를 담은 ClubMemberSimpleInformationResDto 객체
     */
    public ClubArticleSimpleInformationResDto getClubMemberSimpleInformationResDto(Long clubId, Long memberId, CLUB_ARTICLE_CLASSIFICATION classification) {
        List<ClubArticleSimpleInformation> clubArticleSimpleInformations = new ArrayList<>();
        List<ClubMember> clubMembers = clubMemberRepository.findAllByClubId(clubId);

        // 각 클럽 회원에 대해 반복하여 클럽 게시글 조회
        for (ClubMember clubMember : clubMembers) {
            // 해당 클럽 회원이 작성한 특정 분류의 클럽 게시글 조회
            List<ClubArticle> clubArticles = clubArticleRepository.findAllByClubMemberIdAndClassification(clubMember.getClubMemberId(), classification);
            Member authorMember = memberRepository.findById(clubMember.getMemberId()).orElseThrow(EntityNotFoundException::new);

            // 각 게시글에 대해 간단한 정보 생성하여 리스트에 추가
            for (ClubArticle clubArticle : clubArticles) {
                clubArticleSimpleInformations.add(
                        createClubArticleSimpleInformation(authorMember, clubArticle)
                );
            }
        }

        // ClubMemberSimpleInformationResDto 객체 생성 및 반환
        return ClubArticleSimpleInformationResDto.builder()
                .clubArticleSimpleInformations(clubArticleSimpleInformations)
                .build();
    }

    /**
     * 주어진 클럽 ID, 회원 ID 및 게시글 ID를 기반으로 클럽 게시글의 답변에 대한 응답 DTO를 반환합니다.
     *
     * @param clubId    클럽 ID
     * @param memberId  회원 ID
     * @param articleId 게시글 ID
     * @return 클럽 게시글의 답변에 대한 응답 DTO인 ClubArticleAnswerResDto 객체
     * @throws EntityNotFoundException 엔티티를 찾을 수 없을 때 발생하는 예외
     */
    public ClubArticleAnswerResDto getClubArticleAnswerResDto(Long clubId, Long memberId, Long articleId) {
        final ClubArticle clubArticle = clubArticleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new);

        // ClubArticleAnswerResDto 객체 생성 및 반환
        return ClubArticleAnswerResDto.builder()
                .clubArticleTitle(clubArticle.getTitle())
                .clubArticleContent(clubArticle.getContent())
                .clubArticleLikeNumber(String.valueOf(clubArticle.getLikeCount()))
                .clubArticleCommentNumber(String.valueOf(clubArticle.getClubArticleComments().size()))
                .clubArticleDate(clubArticle.getClubArticleDate())
                .build();
    }

    /**
     * 주어진 게시글 ID를 기반으로 클럽 게시글의 건의에 대한 답변을 업데이트하고, 답변 확인 상태를 업데이트한 후 저장합니다.
     *
     * @param articleId                   게시글 ID
     * @param writeSuggestionAnswerReqDto 건의 답변 정보가 담긴 DTO
     * @return 답변이 성공적으로 업데이트되었으면 true를 반환합니다.
     * @throws EntityNotFoundException 엔티티를 찾을 수 없을 때 발생하는 예외
     */
    @Transactional
    public Boolean updateSuggestionAnswerClubArticle(Long articleId, WriteSuggestionAnswerReqDto writeSuggestionAnswerReqDto) {
        final ClubArticle clubArticle = clubArticleRepository.findById(articleId).orElseThrow(EntityNotFoundException::new);

        // 건의 답변 업데이트 및 답변 확인 상태 업데이트
        clubArticle.updateSuggestionAnswer(writeSuggestionAnswerReqDto.getClubSuggestionArticleAnswer());
        clubArticle.updateAnswerCheck(CLUB_ARTICLE_SUGGESTION_CHECK.CONFIRMED);
        final ClubArticle updateClubArticle = clubArticleRepository.save(clubArticle);

        // 업데이트된 게시글이 존재하면 true 반환, 그렇지 않으면 예외 발생
        if (updateClubArticle != null) {
            return true;
        }
        throw new EntityNotFoundException();
    }

    /**
     * 주어진 회원과 클럽 게시글 정보를 기반으로 클럽 게시글의 간단한 정보를 생성합니다.
     *
     * @param authorMember 게시글 작성자 회원 정보
     * @param clubArticle  클럽 게시글 정보
     * @return 생성된 클럽 게시글의 간단한 정보를 담은 ClubArticleSimpleInformation 객체
     */
    private ClubArticleSimpleInformation createClubArticleSimpleInformation(Member authorMember, ClubArticle clubArticle) {
        return ClubArticleSimpleInformation.builder()
                .clubArticleClassification(clubArticle.getClassification().getSort())
                .clubArticleTitle(clubArticle.getTitle())
                .clubArticleAuthorName(authorMember.getFullName())
                .clubArticleDate(clubArticle.getClubArticleDate())
                .clubArticleCommentCount(String.valueOf(clubArticle.getClubArticleComments().size()))
                .clubArticleAnswerCheck(clubArticle.getAnswerCheck().getCheck())
                .build();
    }

    private boolean isNotSame(Integer updateClubArticleLikeCount, Integer clubArticleLikeCount) {
        return updateClubArticleLikeCount != clubArticleLikeCount;
    }


    /**
     * 주어진 회원 ID와 클럽 게시글 댓글 목록을 받아서 해당 회원이 댓글 작성자인지 여부를 확인하고, 필요한 정보로 변환하여 ClubArticleCommentInformation 객체의 리스트를 반환합니다.
     *
     * @param memberId            회원 ID
     * @param clubArticleComments 클럽 게시글 댓글 목록
     * @return ClubArticleCommentInformation 객체의 리스트
     */
    private List<ClubArticleCommentInformation> getClubArticleCommentInformations(Long memberId, List<ClubArticleComment> clubArticleComments) {
        List<ClubArticleCommentInformation> clubArticleCommentInformations = new ArrayList<>();

        for (ClubArticleComment clubArticleComment : clubArticleComments) {
            // comment의 저자을 알려준다.
            String isClubArticleCommentAuthor = getIsClubArticleCommentAuthor(memberId, clubArticleComment);
            // 저자의 Member객체
            Member authMember = memberRepository.findById(clubArticleComment.getMemberId()).orElseThrow(EntityNotFoundException::new);

            clubArticleCommentInformations.add(
                    ClubArticleCommentInformation.builder()
                            .clubArticleCommentId(clubArticleComment.getClubArticleCommentId())
                            .isClubArticleCommentAuthor(isClubArticleCommentAuthor)
                            .clubArticleCommentContent(clubArticleComment.getContent())
                            .clubArticleCommentAuthorName(authMember.getFirstName())
                            .clubArticleCommentDate(clubArticleComment.getCommentDate())
                            .clubArticleCommentLike(String.valueOf(clubArticleComment.getLikeCount()))
                            .build()
            );
        }
        return clubArticleCommentInformations;
    }

    private String getIsClubArticleCommentAuthor(Long memberId, ClubArticleComment clubArticleComment) {
        if (memberId == clubArticleComment.getMemberId()) {

            return AUTHOR_JUDGMENT.AUTHOR.getJudgment();
        }
        return AUTHOR_JUDGMENT.NOT_AUTHOR.getJudgment();
    }

    private String getIsClubArticleAuthor(ClubArticle clubArticle, ClubMember clubMember) {
        if (clubArticle.getClubMemberId() == clubMember.getClubMemberId()) {

            return AUTHOR_JUDGMENT.AUTHOR.getJudgment();
        }
        return AUTHOR_JUDGMENT.NOT_AUTHOR.getJudgment();
    }

    private Boolean isSame(ClubMember clubMember, ClubArticle clubArticle) {
        return clubArticle.getClubMemberId() == clubMember.getClubMemberId();
    }
}
