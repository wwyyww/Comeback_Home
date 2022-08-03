//package TheEarthGuard.ComeBackHome.repository;
//
//import TheEarthGuard.ComeBackHome.domain.Report;
//import java.util.List;
//import java.util.Optional;
//import javax.persistence.EntityManager;
//
//public class JpaReportRepository implements ReportRepository{
//
////    private final EntityManager em;
////
////    public JpaReportRepository(EntityManager em){
////
////        this.em = em;
////    }
////
////    @Override
////    public Report save(Report reportObj) {
////        em.persist(reportObj);
////        return reportObj;
////    }
////
////    @Override
////    public Optional<Report> findByReportId(Long report_id) {
////        Report report = em.find(Report.class, report_id);
////        return Optional.ofNullable(report);
////    }
////
////    @Override
////
////    public Optional<Report> findByCaseId(String case_id) {
////        List<Report> result = em.createQuery("select r from Report r where r.cases.case_id = :case_id", Report.class)
////            .setParameter("case_id", case_id)
////            .getResultList();
////        return result.stream().findAny();
////    }
////
////    @Override
////    public Optional<Report> findByUserId(String user_id) {
////        List<Report> result = em.createQuery("select r from Report r where r.user.id = :user_id", Report.class)
////            .setParameter("user_id", user_id)
////            .getResultList();
////        return result.stream().findAny();
////    }
////
////    @Override
////    public List<Report> findAll() {
////        return em.createQuery("select r from Report r", Report.class)
////            .getResultList();
////    }
//}
