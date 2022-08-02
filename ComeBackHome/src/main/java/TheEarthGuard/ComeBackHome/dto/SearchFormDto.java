package TheEarthGuard.ComeBackHome.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Optional;

@Getter
@Setter
public class SearchFormDto {
    private String missing_name;
    private String search_type;
    private Optional<List<String>> missing_area;
    private Optional<List<String>> missing_sex;
    private Optional<List<String>> missing_age;
}
