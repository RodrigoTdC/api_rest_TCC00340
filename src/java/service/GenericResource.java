/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/WebServices/GenericResource.java to edit this template
 */
package service;

import jakarta.json.Json;
import jakarta.json.JsonBuilderFactory;
import jakarta.json.JsonObject;
import jakarta.json.JsonObjectBuilder;
import jakarta.json.JsonReader;
import jakarta.json.JsonReaderFactory;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.UriInfo;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.StringReader;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import persistence.Edicao;
import persistence.Evento;
import persistence.JPAEdicaoDAO;
import persistence.JPAEventoDAO;
import persistence.JPAUsuarioDAO;
import persistence.Usuario;

/**
 * REST Web Service
 *
 * @author rotos
 */
@Path("generic")
public class GenericResource {

    private final JsonBuilderFactory factory;

    @Context
    private UriInfo context;

    /**
     * Creates a new instance of GenericResource
     */
    public GenericResource() {
        factory = Json.createBuilderFactory(null);
    }

    @GET
    @Path("{caminho}/{ano}")
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject buscarCaminhoAnoJson(@PathParam("caminho") String caminho, @PathParam("ano") int ano) {
        JPAEdicaoDAO dao = new JPAEdicaoDAO();

        Edicao e = dao.buscarCaminhoAno(caminho, ano);

        if (e == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        JsonObjectBuilder builder = factory.createObjectBuilder();

        // <editor-fold defaultstate="collapsed" desc="Passar objeto inteiro para json ao invés de só o id">
        /**
         * JsonObject objEvento = builder.add("id", e.getEvento().getId())
         * .add("nome", e.getEvento().getNome()) .add("sigla",
         * e.getEvento().getSigla()) .add("descricao",
         * e.getEvento().getDescricao()) .add("caminho",
         * e.getEvento().getCaminho()) .build();
         *
         * JsonObject objOrganizador = builder.add("id",
         * e.getOrganizador().getId()) .add("login",
         * e.getOrganizador().getLogin()) .add("nome",
         * e.getOrganizador().getNome()) .add("email",
         * e.getOrganizador().getEmail()) .add("afiliacao",
         * e.getOrganizador().getAfiliacao()) .build();
         *
         * JsonObject objMembros; for (Usuario u : e.getMembros()) { JsonObject
         * objMembroTemp; objMembroTemp = builder.add("id", u.getId())
         * .add("login", e.getOrganizador().getLogin()) .add("nome",
         * e.getOrganizador().getNome()) .add("email",
         * e.getOrganizador().getEmail()) .add("afiliacao",
         * e.getOrganizador().getAfiliacao()) .build();
         *
         * }
         */
        // </editor-fold>
        JsonObject objMembros = null;
        for (Usuario u : e.getMembros()) {
            objMembros = builder.add("id", u.getId()).build();
        }

        JsonObject objEdicao = builder.add("id", e.getId())
                .add("numero", e.getNumero())
                .add("ano", e.getAno())
                .add("dataInicial", new SimpleDateFormat("dd/MM/yyyy").format(e.getDataInicial()))
                .add("dataFinal", new SimpleDateFormat("dd/MM/yyyy").format(e.getDataFinal()))
                .add("cidade", e.getCidade())
                .add("evento", e.getEvento().getId())
                .add("organizador", e.getOrganizador().getId())
                .add("chamada", e.getChamada())
                .add("limiteSubmissao", e.getLimiteSubmissao().toString())
                .add("limiteDivulgacao", e.getLimiteDivulgacao().toString())
                .add("limiteEntrega", e.getLimiteEntrega().toString())
                .add("informacoes", e.getInformacoes())
                .add("membros", objMembros)
                .build();
        return objEdicao;
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response criaEvento(String jsonString, @PathParam("caminho") String caminho) {
        JsonReaderFactory factory = Json.createReaderFactory(null);
        JsonReader jsonReader = factory.createReader(new StringReader(jsonString));
        JsonObject jsonObject = jsonReader.readObject();
        
        JPAEdicaoDAO edicaoDao = new JPAEdicaoDAO();
        JPAEventoDAO eventoDao = new JPAEventoDAO();
        Edicao newEdicao = new Edicao(jsonObject.getInt("numero"), jsonObject.getInt("ano"), LocalDate.parse(jsonObject.getString("dataInicial"), DateTimeFormatter.ofPattern("d/MM/yyyy")), LocalDate.parse(jsonObject.getString("dataFinal"), DateTimeFormatter.ofPattern("d/MM/yyyy")), jsonObject.getString("cidade"), eventoDao.buscar(Long.parseLong(jsonObject.getString("evento"))));
    
        try{
            edicaoDao.salvar(newEdicao);
        }
        catch(Exception ex){
            throw new WebApplicationException(Response.Status.BAD_REQUEST);
        }
        return Response.ok().build();
    }
    
    @DELETE
    @Path("{caminho}/{ano}")
    public Response deletaEdicao(@PathParam("caminho") String caminho, @PathParam("ano") int ano){
        JPAEdicaoDAO dao = new JPAEdicaoDAO();
        Edicao e = dao.buscarCaminhoAno(caminho, ano);

        if (e == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }
        return Response.ok().build();
    }
    
    @PUT
    @Path("{caminho}/{ano}/editar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response editaEdicao(@PathParam("caminho") String caminho, @PathParam("ano") int ano, @QueryParam("organizador") String organizador, @QueryParam("chamada") String chamada, @QueryParam("limiteSubmissao") String limiteSubmissao, @QueryParam("limiteDivulgacao") String limiteDivulgacao, @QueryParam("limiteEntrega") String limiteEntrega, @QueryParam("informacoes") String informacoes, @QueryParam("membros") String membros) {
        JPAEdicaoDAO dao = new JPAEdicaoDAO();
        Edicao e = dao.buscarCaminhoAno(caminho, ano);

        if (e == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        JPAUsuarioDAO usuarioDao = new JPAUsuarioDAO();
        
        if (organizador != null) {
            e.setOrganizador(usuarioDao.buscar(Long.parseLong(organizador)));
        }
        if (chamada != null) {
            e.setChamada(chamada);
        }
        if (limiteSubmissao != null) {
            e.setLimiteSubmissao(LocalDate.parse(limiteSubmissao, DateTimeFormatter.ofPattern("d/MM/yyyy")));
        }
        if (limiteDivulgacao != null) {
            e.setLimiteDivulgacao(LocalDate.parse(limiteDivulgacao, DateTimeFormatter.ofPattern("d/MM/yyyy")));
        }
        if (limiteEntrega != null) {
            e.setLimiteEntrega(LocalDate.parse(limiteEntrega, DateTimeFormatter.ofPattern("d/MM/yyyy")));
        }
        if (informacoes != null) {
            e.setInformacoes(informacoes);
        }
        if (membros != null) {
            String[] arrayMembros = membros.split("\\s*,\\s*");

            
            List<Usuario> listaMembros = new ArrayList();

            for (String membro : arrayMembros) {
                listaMembros.add(usuarioDao.buscar(Long.parseLong(membro)));
            }
            e.setMembros(listaMembros);
        }

        return Response.ok().build();
    }

    @GET
    @Path("{caminho}/{ano}/teste")
    @Produces({MediaType.APPLICATION_JSON})
    public JsonObject testeGet(@PathParam("caminho") String caminho, @PathParam("ano") int ano) {

        JsonObjectBuilder builder = factory.createObjectBuilder();
        JsonObject jsonObject = builder.add("caminho", caminho)
                .add("ano", ano)
                .build();

        return jsonObject;
    }
    
    @PUT
    @Path("testePUT")
    public Response testePut() {
        
        Evento newEvento = new Evento();
        newEvento.setNome("Evento Teste");
        newEvento.setSigla("TESTE");
        newEvento.setDescricao("Descrição teste");
        newEvento.setCaminho("TESTE");
        
        JPAEventoDAO eventoDAO = new JPAEventoDAO();
        eventoDAO.salvar(newEvento);
        
        JPAEdicaoDAO edicaoDAO = new JPAEdicaoDAO();
        Edicao newEdicao = new Edicao(7, 2006, LocalDate.parse("20/06/2006", DateTimeFormatter.ofPattern("d/MM/yyyy")), LocalDate.parse("28/06/2006", DateTimeFormatter.ofPattern("d/MM/yyyy")), "Rio de Janeiro", newEvento);
        edicaoDAO.salvar(newEdicao);
        
        return Response.ok().build();
    }

    /**
     * Retrieves representation of an instance of service.GenericResource
     *
     * @return an instance of java.lang.String
     */
    @GET
    @Produces(jakarta.ws.rs.core.MediaType.APPLICATION_XML)
    public String getXml() {
        //TODO return proper representation object
        throw new UnsupportedOperationException();
    }

    /**
     * PUT method for updating or creating an instance of GenericResource
     *
     * @param content representation for the resource
     */
    @PUT
    @Consumes(jakarta.ws.rs.core.MediaType.APPLICATION_XML)
    public void putXml(String content) {
    }
}
