package TheEarthGuard.ComeBackHome.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class SearchFormDto {
    private String missing_name;
    private String search_type;
    private List<String> missing_sex;
    private List<String> missing_age;
}
