/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

/**
 *
 * @author rotos
 */
public interface EventoDAO {
    void salvar(Evento e);
    Evento buscar(Long id);
    void atualizar(Evento e);
    void deletar(Long id);
}
