package ru.fp.participantservice.dto;

import jakarta.annotation.Nullable;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.fp.participantservice.aop.annotaion.AddUuid;

import java.io.Serializable;

@Builder
@Getter
@Setter
@AddUuid(name = "guid")
public class ParticipantDto implements Serializable {
    private String name;
    private String bic;
    private String typeName;
    private String login;
    private String password;
    private String guid;
    private String email;
    @Nullable private String role;
}
