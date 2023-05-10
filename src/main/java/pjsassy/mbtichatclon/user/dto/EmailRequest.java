package pjsassy.mbtichatclon.user.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class EmailRequest {

    @NotNull(message = "Email 을 입력하세요.")
    private String email;
}
