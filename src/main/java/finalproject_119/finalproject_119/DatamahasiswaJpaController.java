/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package finalproject_119.finalproject_119;

import finalproject_119.finalproject_119.exceptions.NonexistentEntityException;
import finalproject_119.finalproject_119.exceptions.PreexistingEntityException;
import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author Microsoft
 */
//otomatis saat membuat jpa controller dari database
public class DatamahasiswaJpaController implements Serializable {

    public DatamahasiswaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("finalproject_119_finalproject_119_jar_0.0.1-SNAPSHOTPU");

    DatamahasiswaJpaController() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Datamahasiswa datamahasiswa) throws PreexistingEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(datamahasiswa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findDatamahasiswa(datamahasiswa.getId()) != null) {
                throw new PreexistingEntityException("Datamahasiswa " + datamahasiswa + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Datamahasiswa datamahasiswa) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            datamahasiswa = em.merge(datamahasiswa);
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = datamahasiswa.getId();
                if (findDatamahasiswa(id) == null) {
                    throw new NonexistentEntityException("The datamahasiswa with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Datamahasiswa datamahasiswa;
            try {
                datamahasiswa = em.getReference(Datamahasiswa.class, id);
                datamahasiswa.getId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The datamahasiswa with id " + id + " no longer exists.", enfe);
            }
            em.remove(datamahasiswa);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Datamahasiswa> findDatamahasiswaEntities() {
        return findDatamahasiswaEntities(true, -1, -1);
    }

    public List<Datamahasiswa> findDatamahasiswaEntities(int maxResults, int firstResult) {
        return findDatamahasiswaEntities(false, maxResults, firstResult);
    }

    private List<Datamahasiswa> findDatamahasiswaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Datamahasiswa.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Datamahasiswa findDatamahasiswa(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Datamahasiswa.class, id);
        } finally {
            em.close();
        }
    }

    public int getDatamahasiswaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Datamahasiswa> rt = cq.from(Datamahasiswa.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
