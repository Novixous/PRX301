package daos;

import khanglna.dtos.StudentDTO;
import utils.JPAUtil;

import javax.persistence.EntityManager;

public class StudentDAO {
    public StudentDTO findEntity() {
        EntityManager entityManager = JPAUtil.getEntityManagerFactory().createEntityManager();
        entityManager.getTransaction().begin();
        StudentDTO student = (StudentDTO) entityManager.find(StudentDTO.class, 1);
        System.out.println(student);
        entityManager.getTransaction().commit();
        entityManager.close();
        return student;
    }
}
