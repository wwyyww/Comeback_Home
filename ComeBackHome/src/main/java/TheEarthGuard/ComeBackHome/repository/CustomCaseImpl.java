package TheEarthGuard.ComeBackHome.repository;

import TheEarthGuard.ComeBackHome.domain.Case;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository("CaseRepository")
@Transactional
public class CustomCaseImpl implements CustomCaseRepository<Case> {

//    @PersistenceContext
    @Autowired
    EntityManager em;
//
    public CustomCaseImpl(EntityManager em){
        this.em = em;
    }


//    @Override
//    public Case save(Case caseObj) {
//        em.persist(caseObj);
//        return caseObj;
//    }
//
//    @Override
//    public Optional<Case> findByCaseId(Long case_id) {
//        Case caseObj = em.find(Case.class, case_id);
//        return Optional.ofNullable(caseObj);
//    }

//    @Override
//    public Optional<Case> findByFinderId(String finder_id) {
//        List<Case> result = em.createQuery("select c from Case c where c.user.id = :finder_id", Case.class)
//                .setParameter("finder_id", finder_id)
//                .getResultList();
//        return result.stream().findAny();
//    }

    @Override
    public Optional<List<Case>> casebyTime(){
        Optional<List<Case>> caseList = Optional.empty();
        List<Case> result = em.createQuery("select c from Case c ORDER BY c.missingTimeStart DESC ", Case.class).getResultList();
        caseList = Optional.of(result);
        return caseList;
    };


    @Override
    public Optional<List<Case>> searchByMissingName(String missing_name, Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area) {
        Optional<List<Case>> caseList = Optional.empty();
        List<Boolean> sexList = Collections.emptyList();
        List<String> areaList = Collections.emptyList();
        List<String> ageList = Collections.emptyList();
        if (sex != null){
            List<Integer> sexList_Int = sex.get().stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
            sexList = sexList_Int.stream().map(s -> s != 0).collect(Collectors.toList());
        } else {
            sexList = new ArrayList<>(Arrays.asList(false, true));
        }
        if (area != null){
            areaList = area.get().stream().map(s -> s).collect(Collectors.toList());
        } else {
            areaList = new ArrayList<>(Arrays.asList("서울", "경기", "강원", "전북", "전남", "경북", "충남", "충북", "경남", "경북"));
        }
        if (age != null){
            Integer age1 = 0;
            if (age.get().get(0).toString() == "80"){ // 80대 이상
                age1 = Integer.parseInt(age.get().get(0))+100;
            } else {
                age1 = Integer.parseInt(age.get().get(0))+9;
            }
            ageList = new ArrayList<>(Arrays.asList(age.get().get(0).toString(), age1.toString()));
        } else {
            ageList = new ArrayList<>(Arrays.asList("0", "200"));
        }
        System.out.println("sexList: " + sexList);
        System.out.println("areaList: " + areaList);
        System.out.println("areaList: " + ageList);
        System.out.println("name: " + missing_name);
//        List<Case> result2 = em.createQuery("select c from Case c where c.missing_name like :missing_name", Case.class)
//            .setParameter("missing_name", "%" + missing_name +"%")
//            .getResultList();

        List<Case> result = em.createQuery("select c from Case c where c.missingName like :missing_name and c.missingRegion in (:area) and c.missingSex in (:sex) and c.missingAge between :age1 and :age2", Case.class)
                .setParameter("missing_name", "%" + missing_name +"%").setParameter("area", areaList).setParameter("sex", sexList).
                setParameter("age1", Integer.parseInt(ageList.get(0))).setParameter("age2", Integer.parseInt(ageList.get(1))).getResultList();
        caseList = Optional.of(result);
        return caseList;
    }

    @Override
    public Optional<List<Case>> searchByMissingArea(String missing_area, Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area) {
        Optional<List<Case>> caseList = Optional.empty();
        List<Boolean> sexList = Collections.emptyList();
        List<String> areaList = Collections.emptyList();
        List<String> ageList = Collections.emptyList();
        if (sex != null){
            List<Integer> sexList_Int = sex.get().stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
            sexList = sexList_Int.stream().map(s -> s != 0).collect(Collectors.toList());
        } else {
            sexList = new ArrayList<>(Arrays.asList(false, true));
        }
        if (area != null){
            areaList = area.get().stream().map(s -> s).collect(Collectors.toList());
        } else {
            areaList = new ArrayList<>(Arrays.asList("서울", "경기", "강원", "전북", "전남", "경북", "충남", "충북"));
        }
        if (age != null){
            Integer age1 = 0;
            if (age.get().get(0).toString() == "80"){ // 80대 이상
                age1 = Integer.parseInt(age.get().get(0))+100;
            } else {
                age1 = Integer.parseInt(age.get().get(0))+9;
            }
            ageList = new ArrayList<>(Arrays.asList(age.get().get(0).toString(), age1.toString()));
        } else {
            ageList = new ArrayList<>(Arrays.asList("0", "200"));
        }
        System.out.println("sexList: " + sexList);
        System.out.println("areaList: " + areaList);
        System.out.println("areaList: " + ageList);
//        List<Case> result = em.createQuery("select c from Case c where c.missing_area like :missing_area", Case.class)
//                .setParameter("missing_area", "%"+missing_area+"%")
//                .getResultList();

        List<Case> result = em.createQuery("select c from Case c where c.missingArea like :missing_area and c.missingRegion in (:area) and c.missingSex in (:sex) and c.missingAge between :age1 and :age2", Case.class)
                .setParameter("missing_area", "%"+missing_area+"%").setParameter("area", areaList).setParameter("sex", sexList).
                setParameter("age1", Integer.parseInt(ageList.get(0))).setParameter("age2", Integer.parseInt(ageList.get(1))).getResultList();

        caseList = Optional.of(result);
        return caseList;
    }

    @Override
    public Optional<List<Case>> searchByFilters(Optional<List<String>> sex, Optional<List<String>> age, Optional<List<String>> area){
        Optional<List<Case>> caseList = Optional.empty();
        List<Boolean> sexList = Collections.emptyList();
        List<String> areaList = Collections.emptyList();
        List<String> ageList = Collections.emptyList();
        if (sex != null){
            List<Integer> sexList_Int = sex.get().stream().map(s -> Integer.parseInt(s)).collect(Collectors.toList());
            sexList = sexList_Int.stream().map(s -> s != 0).collect(Collectors.toList());
        } else {
            sexList = new ArrayList<>(Arrays.asList(false, true));
        }
        if (area != null){
            areaList = area.get().stream().map(s -> s).collect(Collectors.toList());
        } else {
            areaList = new ArrayList<>(Arrays.asList("서울", "경기", "강원", "전북", "전남", "경북", "충남", "충북"));
        }
        if (age != null){
            Integer age1 = 0;
            if (age.get().get(0).toString() == "80"){ // 80대 이상
                age1 = Integer.parseInt(age.get().get(0))+100;
            } else {
                age1 = Integer.parseInt(age.get().get(0))+9;
            }
            ageList = new ArrayList<>(Arrays.asList(age.get().get(0).toString(), age1.toString()));
        } else {
            ageList = new ArrayList<>(Arrays.asList("0", "200"));
        }

        System.out.println("sexList: " + sexList);
        System.out.println("areaList: " + areaList);
        System.out.println("areaList: " + ageList);
        List<Case> result = em.createQuery("select c from Case c where c.missingRegion in (:area) and c.missingSex in (:sex) and c.missingAge between :age1 and :age2", Case.class)
                .setParameter("area", areaList).setParameter("sex", sexList).setParameter("age1", Integer.parseInt(ageList.get(0))).setParameter("age2", Integer.parseInt(ageList.get(1))).getResultList();

        caseList = Optional.of(result);

        return caseList;
    }

    @Override
    public List<Case> findAll() {
        return em.createQuery("select c from Case c", Case.class)
                .getResultList();
    }
}
