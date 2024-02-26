package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.response.*;
import back.springbootdeveloper.seungchan.entity.*;
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
}
