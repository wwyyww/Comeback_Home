package TheEarthGuard.ComeBackHome.repository;

import TheEarthGuard.ComeBackHome.domain.Case;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;

public class JpaCaseRepository implements CaseRepository{

    private final EntityManager em;

    public JpaCaseRepository(EntityManager em){
        this.em = em;
    }


    @Override
    public Case save(Case caseObj) {
        em.persist(caseObj);
        return caseObj;
    }

    @Override
    public Optional<Case> findByCaseId(Long case_id) {
        Case caseObj = em.find(Case.class, case_id);
        return Optional.ofNullable(caseObj);
    }

    @Override
    public Optional<Case> findByFinderId(String finder_id) {
        List<Case> result = em.createQuery("select c from Case c where c.user.id = :finder_id", Case.class)
            .setParameter("finder_id", finder_id)
            .getResultList();
        return result.stream().findAny();
    }

    @Override
    public Optional<Case> findByMissingName(String missing_name) {
        List<Case> result = em.createQuery("select c from Case c where c.missing_name = :missing_name", Case.class)
            .setParameter("missing_name", missing_name)
            .getResultList();
        return result.stream().findAny();
    }

    @Override
    public List<Case> findAll() {
        return em.createQuery("select c from Case c", Case.class)
            .getResultList();
    }
}
