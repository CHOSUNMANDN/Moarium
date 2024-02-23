package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.Club;
import back.springbootdeveloper.seungchan.repository.ClubRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ClubInformationService {

    private final ClubRepository clubRepository;

    @Transactional
    public Club findById(long id){
        return clubRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Club not found"));
    }

}
