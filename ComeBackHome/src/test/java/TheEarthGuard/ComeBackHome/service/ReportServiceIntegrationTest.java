package TheEarthGuard.ComeBackHome.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import TheEarthGuard.ComeBackHome.domain.Report;
import TheEarthGuard.ComeBackHome.respoitory.ReportRepository;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Transactional
public class ReportServiceIntegrationTest {
    @Autowired
    ReportService reportService;
    @Autowired ReportRepository reportRepository;


    @Test
    public void 증언남기기() throws Exception {
        //Given
        Report report = new Report("CaseID1","userID1", "wa", "wt", "wTxt", "wPic", "false");

        //When
        Long report_id = reportService.testify(report);
        System.out.println("[TEST] Report " + report.getReport_id());

        //Then
        Report findReport = reportRepository.findByReportId(report_id).get();
        assertEquals(report.getReport_id(), findReport.getReport_id());
    }

//    @Test
//    public void 중복_증언_예외() throws Exception {
//        //Given
//        Report report  = new Report();
//        member1.setName("spring");
//        Member member2 = new Member();
//        member2.setName("spring");
//        //When
//        memberService.join(member1);
//        IllegalStateException e = assertThrows(IllegalStateException.class,
//            () -> memberService.join(member2));//예외가 발생해야 한다.
//        assertThat(e.getMessage()).isEqualTo("이미 존재하는 회원입니다.");
//    }
}