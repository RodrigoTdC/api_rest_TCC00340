/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import java.util.List;

/**
 *
 * @author rotos
 */
public class JPAEdicaoDAO implements EdicaoDAO{

    private EntityManager em;
    
    public JPAEdicaoDAO(){
        
    }
    
    @Override
    public void salvar(Edicao e) {
        em = JPAUtil.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(e);
        et.commit();
        em.close();
    }

    @Override
    public Edicao buscar(Long id) {
        em = JPAUtil.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Edicao e = em.find(Edicao.class, id);
        et.commit();
        em.close();
        return e;    }

    @Override
    public void atualizar(Edicao e) {
        em = JPAUtil.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            em.merge(e);
            et.commit();
        } catch (Exception ex) {
            et.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public void deletar(Long id) {
        em = JPAUtil.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        try {
            Edicao e = em.find(Edicao.class, id);
            if (e != null) {
                em.remove(e);
            }
            et.commit();
        }
        catch (Exception ex){
            et.rollback();
            throw ex;
        } finally {
            em.close();
        }
    }

    @Override
    public List<Edicao> listar() {
        em = JPAUtil.getEntityManager();
        String jpqlQuery = "SELECT e FROM edicao e";
        Query query = em.createQuery(jpqlQuery);
        List<Edicao> e = query.getResultList();
        em.close();
        return e;
    }

    @Override
    public Edicao buscarCaminhoAno(String caminho, int ano) {
        em = JPAUtil.getEntityManager();
        String jpqlQuery = "SELECT e FROM edicao e WHERE e.caminho = " + caminho + " AND e.ano = " + ano;
        Query query = em.createQuery(jpqlQuery);
        Edicao e = (Edicao) query.getSingleResult();
        em.close();
        System. out. println(e);
        //return null;
        return e;
    }
    
}
