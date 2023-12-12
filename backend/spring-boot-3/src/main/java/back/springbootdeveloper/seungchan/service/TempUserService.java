package back.springbootdeveloper.seungchan.service;

import back.springbootdeveloper.seungchan.entity.TempUser;
import back.springbootdeveloper.seungchan.dto.request.TempUserFormRequest;
import back.springbootdeveloper.seungchan.dto.response.NewUsersResponse;
import back.springbootdeveloper.seungchan.repository.TempUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class TempUserService {
    private final TempUserRepository tempUserRepository;

    public void save(TempUserFormRequest requestUserForm) {
        TempUser newTempUser = requestUserForm.toEntity();
        tempUserRepository.save(newTempUser);
    }

    public List<NewUsersResponse> findAllNewUsers() {
        List<NewUsersResponse> newUsersResponseList = new ArrayList<>();
        List<TempUser> tempUserList = tempUserRepository.findAll();
        for (TempUser tempUser : tempUserList) {
            Long id = tempUser.getId();
            String email = tempUser.getEmail();
            String name = tempUser.getName();
            String applicationDate = tempUser.getApplicationDate();
            newUsersResponseList.add(new NewUsersResponse(id, email, name, applicationDate));
        }
        return newUsersResponseList;
    }

    public TempUser findNewUsers(long id) {
        return tempUserRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected new user"));
    }

    public TempUser removeTempUserByEmail(String emailOfNewUser) {
        TempUser tempUser = tempUserRepository.findByEmail(emailOfNewUser)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected new user"));
        tempUserRepository.deleteByEmail(emailOfNewUser);

        return tempUser;
    }

    public TempUser removeTempUserById(Long idOfNewUser) {
        TempUser tempUser = tempUserRepository.findById(idOfNewUser)
                .orElseThrow(() -> new IllegalArgumentException("Unexpected new user"));
        tempUserRepository.deleteById(idOfNewUser);

        return tempUser;
    }
}