/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

/**
 *
 * @author rotos
 */
public class JPAUsuarioDAO implements UsuarioDAO{

    private EntityManager em;
    
    @Override
    public Usuario buscar(Long id) {
        em = JPAUtil.getEntityManager();
        EntityTransaction et = em.getTransaction();
        et.begin();
        Usuario u = em.find(Usuario.class, id);
        et.commit();
        em.close();
        return u;
    }

}
