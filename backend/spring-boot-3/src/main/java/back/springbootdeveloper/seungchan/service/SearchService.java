package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.response.SearchResult;
import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SearchService {

  private final ClubRepository clubRepository;
  private final ImageService imageService;

  /**
   * 주어진 검색어와 일치하는 클럽 정보를 검색하여 결과를 반환합니다.
   *
   * @param search 검색어
   * @return 검색 결과 리스트
   */
  // TODO: 3/27/24 와일드 카드 구현
  public List<SearchResult> getSearchResults(final String search) {
    List<SearchResult> searchResults = new ArrayList<>();
    List<Club> clubs = clubRepository.findAll();

    for (final Club club : clubs) {
      String clubName = club.getClubName();
      if (clubName.contains(search) && !search.isEmpty()) {
        String clubProfileImage = imageService.getImagesAsBase64(club.getClubProfileImage());
        searchResults.add(
            SearchResult.builder()
                .clubName(clubName)
                .clubProfileImage(clubProfileImage)
                .build()
        );
      }
    }

    return searchResults;
  }
}
