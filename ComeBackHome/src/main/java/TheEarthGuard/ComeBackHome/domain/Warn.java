package TheEarthGuard.ComeBackHome.domain;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Table(name="warn")
@Getter
@Setter
public class Warn extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @NotNull
    @JoinColumn(name="warnSender")
    private User warnSender;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String warnReason;

    @Builder
    @SuppressFBWarnings(justification = "Generated code")
    public Warn(Long id, User warnSender, LocalDateTime warnDate, String warnReason) {
        this.id = id;
        this.warnSender = warnSender;
        this.warnReason = warnReason;
    }

    public Warn() {

    }
}
