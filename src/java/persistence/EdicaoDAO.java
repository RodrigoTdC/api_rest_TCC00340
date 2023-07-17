/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence;

import java.util.List;

/**
 *
 * @author rotos
 */
public interface EdicaoDAO {
    
    void salvar (Edicao e); //Salva a edição
    Edicao buscar (Long id);    //Busca uma edição pelo id
    void atualizar (Edicao e);  //Substitui uma edição por uma versão atualizada
    void deletar (Long id); //Deleta uma edição pelo id
    
    List<Edicao> listar();  //Lista todas as edições
    Edicao buscarCaminhoAno(String caminho, int ano); //Busca uma edição pelo seu caminho e ano de ocorrência
}
