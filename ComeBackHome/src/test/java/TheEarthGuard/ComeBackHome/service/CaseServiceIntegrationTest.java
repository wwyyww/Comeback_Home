package TheEarthGuard.ComeBackHome.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.repository.CaseRepository;
import java.sql.Timestamp;
import java.util.Optional;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class CaseServiceIntegrationTest {
    @Autowired
    CaseService caseService;
    @Autowired
    CaseRepository caseRepository;

    @Test
    public void 사건_등록하기() throws Exception {
        //Given
        String finder_id = "finderID123";
        String missing_pic = "/picture1.png";
        String missing_name = "홍길동";
        Integer missing_age = 18;
        Boolean missing_sex = true;
        String missing_desc = "홍길동씨를 찾습니다. 나이는 n살, 사는 지역은 m으로...";
        String missing_area = "한양구";
        Double missing_lat = 32.123245;
        Double missing_lng = 18.123245;
        Timestamp missing_time = Timestamp.valueOf("1880-12-12 01:24:23");

        //When
        Case caseObj = Case.builder()
            .finder_id(finder_id)
            .missing_pic(missing_pic)
            .missing_name(missing_name)
            .missing_age(missing_age)
            .missing_sex(missing_sex)
            .missing_desc(missing_desc)
            .missing_area(missing_area)
            .missing_lat(missing_lat) // 계산 필요
            .missing_lng(missing_lng) // 계산 필요
            .missing_time(missing_time)
            .build();

        Long case_id = caseService.UploadCase(caseObj);
        System.out.println("[TEST] Case caseId " + caseObj.getCase_id());

        //Then
        Case findCase = caseRepository.findByCaseId(case_id).get();
        assertEquals(caseObj.getCase_id(), findCase.getCase_id());
    }


    @Test
    public void 사건_실종자이름_검색하기() throws Exception {//Given
        String finder_id = "finderID123";
        String missing_pic = "/picture1.png";
        Integer missing_age = 18;
        Boolean missing_sex = true;
        String missing_desc = "홍길동씨를 찾습니다. 나이는 n살, 사는 지역은 m으로...";
        String missing_area = "한양구";
        Double missing_lat = 32.123245;
        Double missing_lng = 18.123245;
        Timestamp missing_time = Timestamp.valueOf("1880-12-12 01:24:23");

        //When
        Case caseObj1 = Case.builder()
            .finder_id(finder_id)
            .missing_pic(missing_pic)
            .missing_name("홍길동")
            .missing_age(missing_age)
            .missing_sex(missing_sex)
            .missing_desc(missing_desc)
            .missing_area(missing_area)
            .missing_lat(missing_lat) // 계산 필요
            .missing_lng(missing_lng) // 계산 필요
            .missing_time(missing_time)
            .build();

        Case caseObj2 = Case.builder()
            .finder_id(finder_id)
            .missing_pic(missing_pic)
            .missing_name("김길동")
            .missing_age(missing_age)
            .missing_sex(missing_sex)
            .missing_desc(missing_desc)
            .missing_area(missing_area)
            .missing_lat(missing_lat) // 계산 필요
            .missing_lng(missing_lng) // 계산 필요
            .missing_time(missing_time)
            .build();

        Case caseObj3 = Case.builder()
            .finder_id(finder_id)
            .missing_pic(missing_pic)
            .missing_name("이길동")
            .missing_age(missing_age)
            .missing_sex(missing_sex)
            .missing_desc(missing_desc)
            .missing_area(missing_area)
            .missing_lat(missing_lat) // 계산 필요
            .missing_lng(missing_lng) // 계산 필요
            .missing_time(missing_time)
            .build();


        //When
        Long case_id1 = caseService.UploadCase(caseObj1);
        Long case_id2 = caseService.UploadCase(caseObj2);
        Long case_id3 = caseService.UploadCase(caseObj3);
        System.out.println("[TEST] Case1 caseId " + caseObj1.getCase_id() + caseObj1.getMissing_name());
        System.out.println("[TEST] Case2 caseId " + caseObj2.getCase_id() + caseObj2.getMissing_name());
        System.out.println("[TEST] Case3 caseId " + caseObj3.getCase_id() + caseObj3.getMissing_name());


        //Then
        System.out.println("[TEST] " +  caseRepository.findByMissingName("홍길동"));
        Optional<Case> findCaseByName = caseRepository.findByMissingName("홍길동");
        System.out.println("[TEST] findCaseByName " + findCaseByName);
    }
}
