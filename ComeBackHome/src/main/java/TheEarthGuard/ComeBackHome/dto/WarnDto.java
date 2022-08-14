package TheEarthGuard.ComeBackHome.dto;

import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.domain.Role;
import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.domain.Warn;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@NoArgsConstructor
public class WarnDto {

    private User warnSender;

    @NotBlank(message = "신고 이유는 필수 입력값입니다.")
    private String warnReason;

    private Report reports;

    public WarnDto(User warnSender, String warnReason, Report reports) {
        this.warnSender = warnSender;
        this.warnReason = warnReason;
        this.reports = reports;
    }

    public Warn toEntity() {
        return Warn.builder()
                .warnSender(this.warnSender)
                .warnReason(this.warnReason)
                .reports(this.reports)
                .build();
    }
    
    
}
