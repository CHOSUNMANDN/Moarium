package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.dto.response.ApplyForm;
import back.springbootdeveloper.seungchan.entity.ApplyEntity;
import back.springbootdeveloper.seungchan.repository.ApplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class ApplyService {
    private final ApplyRepository applyRepository;

    //팀 지원서를 DB로 보냄
    public ApplyEntity clubApply(ApplyForm applyForm){
       ApplyEntity applyEntity = applyForm.toEntity();

        return applyRepository.save(applyEntity);
    }
}
