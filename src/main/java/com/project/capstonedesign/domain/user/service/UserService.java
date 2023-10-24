package com.project.capstonedesign.domain.user.service;

import com.project.capstonedesign.domain.user.Role;
import com.project.capstonedesign.domain.user.User;
import com.project.capstonedesign.domain.user.dto.UserUpdatedDto;
import com.project.capstonedesign.domain.user.repository.UserRepository;
import com.project.capstonedesign.domain.user.dto.UserSignUpDto;
import com.project.capstonedesign.domain.user.exception.NotFoundUserException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * userId로 회원 조회
     * @param userId
     * @return
     */
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public User findById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundUserException(String.format("There is no userId: %s", userId)));
    }

    /**
     * 전체 회원 조회
     * @return
     */
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public List<User> findAll() {
        return userRepository.findAll();
    }

    /**
     * 회원정보 수정
     * @param userId
     * @return
     */
    @org.springframework.transaction.annotation.Transactional
    public Long updateNickname(Long userId, UserUpdatedDto userUpdatedDto) {
        User user = findById(userId);
        User update = user.updateNickname(
                userUpdatedDto.getNickname()
        );
        return update.getUserId();
    }

    @Transactional
    public Long updateImageUrl(Long userId, UserUpdatedDto userUpdatedDto) {
        User user = findById(userId);
        User update = user.updateImageUrl(
                userUpdatedDto.getImageUrl()
        );
        return update.getUserId();
    }

    /**
     * 회원 탈퇴
     * @param userId
     */
    @org.springframework.transaction.annotation.Transactional
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    /**
     * 회원가입
     * @param userSignUpDto
     * @throws Exception
     */
    public void join(UserSignUpDto userSignUpDto) throws Exception {
        if (userRepository.findByEmail(userSignUpDto.getEmail()).isPresent()) {
            throw new Exception("이미 존재하는 이메일입니다.");
        }

        if (userRepository.findByNickname(userSignUpDto.getNickname()).isPresent()) {
            throw new Exception("이미 존재하는 닉네임입니다.");
        }

        User user = User.builder()
                .nickname(userSignUpDto.getNickname())
                .email(userSignUpDto.getEmail())
                .password(userSignUpDto.getPassword())
                .name(userSignUpDto.getName())
                .cellphone(userSignUpDto.getCellphone())
                .role(Role.USER)
                .build();

        user.passwordEncode(passwordEncoder);
        userRepository.save(user);
    }

}
