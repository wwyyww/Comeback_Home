package TheEarthGuard.ComeBackHome.repository;

import TheEarthGuard.ComeBackHome.domain.Case;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public class CaseCustomRepositoryImpl implements CaseCustomRepository{

    @Autowired
    EntityManager em;

    @Override
    public Optional<List<Case>> searchByMissingName(String missing_name, Optional<List<String>> sex,
        Optional<List<String>> age, Optional<List<String>> area, String feature) {
        Optional<List<Case>> caseList = Optional.empty();
        List<Boolean> sexList = Collections.emptyList();
        List<String> areaList = Collections.emptyList();
        List<String> ageList = Collections.emptyList();
        List<Integer> featureList = Collections.emptyList();

        if (feature != null){
            featureList = new ArrayList<>(Arrays.asList(Integer.parseInt(feature)));
        } else {
            featureList = new ArrayList<>(Arrays.asList(1,2,3,4));
        }
        if (sex != null){
            List<Integer> sexList_Int = sex.get().stream().map(s -> Integer.parseInt(s)).collect(
                Collectors.toList());
            sexList = sexList_Int.stream().map(s -> s != 0).collect(Collectors.toList());
        } else {
            sexList = new ArrayList<>(Arrays.asList(false, true));
        }
        if (area != null){
            areaList = area.get().stream().map(s -> s).collect(Collectors.toList());
        } else {
            areaList = new ArrayList<>(
                Arrays.asList("서울", "경기", "강원", "전북", "전남", "경북", "충남", "충북", "경남", "경북"));
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
        System.out.println("featureList: " + featureList);
//        List<Case> result2 = em.createQuery("select c from Case c where c.missing_name like :missing_name", Case.class)
//            .setParameter("missing_name", "%" + missing_name +"%")
//            .getResultList();

        List<Case> result = em.createQuery("select c from Case c where c.missingName like :missing_name and c.missingRegion in (:area) and c.missingSex in (:sex) and c.missingAge between :age1 and :age2 and c.missingFeature in (:feature)", Case.class)
            .setParameter("missing_name", "%" + missing_name +"%").setParameter("area", areaList).setParameter("sex", sexList).setParameter("feature", featureList).
                setParameter("age1", Integer.parseInt(ageList.get(0))).setParameter("age2", Integer.parseInt(ageList.get(1))).getResultList();
        caseList = Optional.of(result);
        return caseList;
    }

    @Override
    public Optional<List<Case>> searchByMissingArea(String missing_area, Optional<List<String>> sex,
        Optional<List<String>> age, Optional<List<String>> area, String feature) {
        Optional<List<Case>> caseList = Optional.empty();
        List<Boolean> sexList = Collections.emptyList();
        List<String> areaList = Collections.emptyList();
        List<String> ageList = Collections.emptyList();
        List<Integer> featureList = Collections.emptyList();

        if (feature != null){
            featureList = new ArrayList<>(Arrays.asList(Integer.parseInt(feature)));
        } else {
            featureList = new ArrayList<>(Arrays.asList(1,2,3,4));
        }
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
        System.out.println("featureList: " + featureList);
//        List<Case> result = em.createQuery("select c from Case c where c.missing_area like :missing_area", Case.class)
//                .setParameter("missing_area", "%"+missing_area+"%")
//                .getResultList();

        List<Case> result = em.createQuery("select c from Case c where c.missingArea like :missing_area and c.missingRegion in (:area) and c.missingSex in (:sex) and c.missingAge between :age1 and :age2 and c.missingFeature in (:feature)", Case.class)
            .setParameter("missing_area", "%"+missing_area+"%").setParameter("area", areaList).setParameter("sex", sexList).setParameter("feature", featureList).
                setParameter("age1", Integer.parseInt(ageList.get(0))).setParameter("age2", Integer.parseInt(ageList.get(1))).getResultList();

        caseList = Optional.of(result);
        return caseList;
    }

    @Override
    public Optional<List<Case>> searchByFilters(Optional<List<String>> sex,
        Optional<List<String>> age, Optional<List<String>> area, String feature) {
        Optional<List<Case>> caseList = Optional.empty();
        List<Boolean> sexList = Collections.emptyList();
        List<String> areaList = Collections.emptyList();
        List<String> ageList = Collections.emptyList();
        List<Integer> featureList = Collections.emptyList();

        if (feature != null){
            featureList = new ArrayList<>(Arrays.asList(Integer.parseInt(feature)));
        } else {
            featureList = new ArrayList<>(Arrays.asList(1,2,3,4));
        }
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
        System.out.println("featureList: " + featureList);
        List<Case> result = em.createQuery("select c from Case c where c.missingRegion in (:area) and c.missingSex in (:sex) and c.missingAge between :age1 and :age2 and c.missingFeature in (:feature)", Case.class)
            .setParameter("area", areaList).setParameter("sex", sexList).setParameter("age1", Integer.parseInt(ageList.get(0))).setParameter("age2", Integer.parseInt(ageList.get(1)))
                .setParameter("feature", featureList).getResultList();

        caseList = Optional.of(result);

        return caseList;
    }

    @Override
    public Optional<List<Case>> casebyTime() {
        Optional<List<Case>> caseList = Optional.empty();
        List<Case> result = em.createQuery("select c from Case c ORDER BY c.missingTimeStart DESC ", Case.class).getResultList();
        caseList = Optional.of(result);
        return caseList;
    }
}
