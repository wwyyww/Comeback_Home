package TheEarthGuard.ComeBackHome.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import TheEarthGuard.ComeBackHome.domain.Case;
import TheEarthGuard.ComeBackHome.respoitory.CaseRepository;

import java.sql.Timestamp;
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
        String dateTime = "1900-01-01 01:24:23";
        Timestamp missing_time = Timestamp.valueOf(dateTime);

        Case caseObj = new Case("finder1",false,0,1,"/pic/picture1","홍길동",20,false,"조선시대때 실종된 홍길동을 찾습니다.", "한양", 15.2345, 38.4353, missing_time);
        System.out.println("[TEST] Case finder_id" + caseObj.getFinder_id());

        //When
        Long case_id = caseService.UploadCase(caseObj);
        System.out.println("[TEST] Case caseId " + caseObj.getCase_id());

        //Then
        Case findCase = caseRepository.findByCaseId(case_id).get();
        assertEquals(caseObj.getCase_id(), findCase.getCase_id());
    }


    @Test
    public void 사건_실종자이름_검색하기() throws Exception {
        //Given
        Timestamp missing_time1 = Timestamp.valueOf("1900-01-01 01:24:23");
        Timestamp missing_time2 = Timestamp.valueOf("1950-12-12 01:24:23");
        Timestamp missing_time3 = Timestamp.valueOf("1880-12-12 01:24:23");

        Case caseObj1 = new Case("finder1",false,0,1,"/pic/picture1","홍길동",20,false,"조선시대때 실종된 홍길동을 찾습니다.", "한양", 15.2345, 38.4353, missing_time1);
        Case caseObj2 = new Case("finder2",false,0,1,"/pic/picture2","김길동",20,true,"조선시대때 실종된 김길동을 찾습니다.", "한양", 10.2345, 35.4353, missing_time2);
        Case caseObj3= new Case("finder3",false,0,1,"/pic/picture3","홍길동",20,false,"조선시대때 실종된 홍길동을 찾습니다.", "부산", 8.2345, 38.4353, missing_time3);
//        System.out.println("[TEST] Case finder_id" + caseObj1.getFinder_id());


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
