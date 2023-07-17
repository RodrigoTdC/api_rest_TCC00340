/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 *
 * @author rotos
 */
public class JPAUtil {
    
    private static EntityManagerFactory emf;

    public static EntityManager getEntityManager(){
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("projetoDACPU");
        }
        return emf.createEntityManager();        
    }
    
    public static void closeEntityManager(){
        emf.close();
    }
}
