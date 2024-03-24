package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.ClubMemberCustomInformation;
import back.springbootdeveloper.seungchan.entity.ClubMemberInformation;
import back.springbootdeveloper.seungchan.filter.exception.judgment.EntityNotFoundException;
import back.springbootdeveloper.seungchan.repository.ClubMemberInformationRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ClubMemberCustomService {

  @Autowired
  private ClubMemberInformationRepository clubMemberInformationRepository;

  public List<String> findAllCustomContentByClubMemberInformation(
      final Long clubMemberInformationId) {
    ClubMemberInformation clubMemberInformation = clubMemberInformationRepository.findById(
        clubMemberInformationId).orElseThrow(
        EntityNotFoundException::new);
    List<ClubMemberCustomInformation> clubMemberCustomInformations = clubMemberInformation.getClubMemberCustomInformations();

    return clubMemberCustomInformations.
        stream().map(ClubMemberCustomInformation::getCustomContent)
        .collect(Collectors.toList());
  }
}
