package TheEarthGuard.ComeBackHome.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PUBLIC)
@Table(name="files")
public class FileEntity extends BaseTimeEntity{
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    private String savedFileName;
    private String orgFileName;
    private String fileType;
    private Long fileSize;

    @Builder
    public FileEntity(Long id, String savedFileName, String orgFileName,
        String fileType, Long fileSize) {
        this.id = id;
        this.savedFileName = savedFileName;
        this.orgFileName = orgFileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
    }

}
