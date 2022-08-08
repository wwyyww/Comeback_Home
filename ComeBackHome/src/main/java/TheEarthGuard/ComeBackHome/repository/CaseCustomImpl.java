package TheEarthGuard.ComeBackHome.repository;

import TheEarthGuard.ComeBackHome.domain.Case;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CaseCustomImpl implements CaseCustomRepository<Case>{

    @Autowired
    private final EntityManager em;

    public CaseCustomImpl(EntityManager em){
        this.em = em;
    }
//
////
////    @Override
////    public Case save(Case caseObj) {
////        em.persist(caseObj);
////        return caseObj;
////    }
//
////    @Override
////    public Optional<Case> findByCaseId(Long case_id) {
////        Case caseObj = em.find(Case.class, case_id);
////        return Optional.ofNullable(caseObj);
////    }
//
////    @Override
////    public Optional<Case> findByUserId(Long user_id) {
////        List<Case> result = em.createQuery("select c from Case c where c.user.id = :user_id", Case.class)
////            .setParameter("user_id", user_id)
////            .getResultList();
////        return result.stream().findAny();
////    }
//
//    @Override
//    public Optional<List<Case>> findfilter(Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area){
//        List<Case> results = Collections.emptyList();
//        String area_final = "%";
//
//        if(area != null){
//            area_final = "%" + area.get().get(0).toString()+"%";
//        }
//
//        if(sex != null && age != null){
//            if (Integer.parseInt(age.get().get(0)) == 80){
//                results = em.createQuery("select c from Case c where c.missingArea like :area and c.missingSex = :sex and c.missingAge >= :age", Case.class)
//                    .setParameter("area", area_final)
//                    .setParameter("age", Integer.parseInt(age.get().get(0)))
//                    .setParameter("sex", Boolean.parseBoolean(sex.get().get(0)))
//                    .getResultList();
//            } else {
//                results = em.createQuery("select c from Case c where c.missingArea like :area and c.missingSex = :sex and c.missingAge >= :age and c.missingAge < :age2", Case.class)
//                    .setParameter("area", area_final)
//                    .setParameter("age", Integer.parseInt(age.get().get(0)))
//                    .setParameter("age2", (Integer.parseInt(age.get().get(0))+10))
//                    .setParameter("sex", Boolean.parseBoolean(sex.get().get(0)))
//                    .getResultList();
//            }
//        }
//        System.out.println(results);
//        return Optional.of(results);
//    }
//
//    @Override
//    public Optional<List<Case>> findMissingNameFilter(String missingName, Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area) {
//        List<Case> result = em.createQuery("select c from Case c where c.missingName like :missingName", Case.class)
//            .setParameter("missingName", "%" + missingName +"%")
//            .getResultList();
//
//        if (sex != null){
//
//            List<Integer> sexList_Int = sex.get().stream().map(s -> Integer.parseInt(s)).collect(
//                Collectors.toList());
//            List<Boolean> sexList = sexList_Int.stream().map(s -> s != 0).collect(Collectors.toList());
//            System.out.println(sexList);
//            result = em.createQuery("select c from Case c where c.missingSex in (:sex)", Case.class)
//                .setParameter("sex", sexList)
//                .getResultList();
//        }
//        if (area != null){
//            List<String> areaList = area.get().stream().map(s -> "%"+s+"%").collect(Collectors.toList());
//            System.out.println(areaList);
//            result = em.createQuery("select c from Case c where c.missingArea in (:area)", Case.class)
//                .setParameter("area", areaList)
//                .getResultList();
//        }
//
//        Optional<List<Case>> caseList = Optional.of(result);
//        return caseList;
//    }
//
//    @Override
//    public Optional<List<Case>> findMissingAreaFilter(String missingArea) {
//        List<Case> result = em.createQuery("select c from Case c where c.missingArea like :missingArea", Case.class)
//            .setParameter("missingArea", "%"+missingArea+"%")
//            .getResultList();
//        Optional<List<Case>> caseList = Optional.of(result);
//        return caseList;
//    }
//
//    @Override
//    public List<Case> findAll() {
//        return em.createQuery("select c from Case c", Case.class)
//            .getResultList();
//    }

}
