package pjsassy.mbtichatclon.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pjsassy.mbtichatclon.common.httpMessageController.code.ErrorCode;
import pjsassy.mbtichatclon.common.httpMessageController.exception.CustomIllegalArgumentException;
import pjsassy.mbtichatclon.common.util.RedisUtil;
import pjsassy.mbtichatclon.jwt.TokenProvider;
import pjsassy.mbtichatclon.jwt.dto.TokenResponse;
import pjsassy.mbtichatclon.user.dto.EmailRequest;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UserUtilService {

    private final TokenProvider tokenProvider;
    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;

    @Transactional
    public void sendEmail(EmailRequest emailRequest) {
        // 인증 번호 생성
        Random random = new Random();
        String code = String.valueOf(random.nextInt(888888) + 111111);// 111111 ~ 888888

        // 이메일 발송
        sendAuthEmail(emailRequest.getEmail(), code);

    }

    private void sendAuthEmail(String email, String code) {
        String subject = "MBTI CHAT";
        String test = "MBTI CHAT 인증번호는 " + code + "입니다. <br/>";

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        } catch (MessagingException e) {
            e.printStackTrace();
        }

        // 유효 시간 설정
        redisUtil.setDataExpire(code, email, 60*5L); //5분

    }

    public TokenResponse reissue(String accessToken, String refreshToken) {
        // 검증
        if (!tokenProvider.validateToken(refreshToken)) {
            throw new CustomIllegalArgumentException(ErrorCode.INVALID_TOKEN);
        }

        // AccessToken 에서 userId 가져오기
        Authentication authentication = tokenProvider.getAuthentication(accessToken);
        String userId = authentication.getName();

        // 저장소에서 userId 를 기반으로 RefreshToken 값 가져오기
        String findRefreshToken = redisUtil.getData(userId);
        if (findRefreshToken == null) {
            throw new CustomIllegalArgumentException(ErrorCode.INVALID_TOKEN);
        }

        // RefreshToken 일치여부 검사
        if (!refreshToken.equals(findRefreshToken)) {
            throw new CustomIllegalArgumentException(ErrorCode.NO_MATCHES_INFO);
        }

        // 새로운 토큰 생성
        TokenResponse tokenResponse = tokenProvider.generateTokenDto(authentication);

        // 저장소 정보 업데이트
        redisUtil.setDataExpire(userId, tokenResponse.getRefreshToken(), 1000 * 60 * 60 * 24 * 7);

        return tokenResponse;
    }
}
