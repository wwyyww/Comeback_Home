package TheEarthGuard.ComeBackHome.dto;

import TheEarthGuard.ComeBackHome.domain.User;
import TheEarthGuard.ComeBackHome.domain.Warn;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import javax.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WarnDto {

    private User warnSender;

    @NotBlank(message = "신고 이유는 필수 입력값입니다.")
    private String warnReason;

    @SuppressFBWarnings(justification = "Generated code")
    public WarnDto(User warnSender, String warnReason)  {
        this.warnSender = warnSender;
        this.warnReason = warnReason;
    }

    public Warn toEntity() {
        return Warn.builder()
                .warnSender(this.warnSender)
                .warnReason(this.warnReason)
                .build();
    }

    
    
}
