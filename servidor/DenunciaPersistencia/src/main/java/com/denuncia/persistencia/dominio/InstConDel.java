package com.denuncia.persistencia.dominio;
// Generated 24/08/2014 12:41:26 AM by Hibernate Tools 3.6.0



/**
 * InstConDel generated by hbm2java
 */
public class InstConDel  implements java.io.Serializable {


     private Integer idInstConDel;
     private CatInstitucion catInstitucion;
     private CatDelegacion catDelegacion;
     private CatContacto catContacto;
     private CatCategoriaDenuncia catCategoriaDenuncia;

    public InstConDel() {
    }

    public InstConDel(CatInstitucion catInstitucion, CatDelegacion catDelegacion, CatContacto catContacto, CatCategoriaDenuncia catCategoriaDenuncia) {
       this.catInstitucion = catInstitucion;
       this.catDelegacion = catDelegacion;
       this.catContacto = catContacto;
       this.catCategoriaDenuncia = catCategoriaDenuncia;
    }
   
    public Integer getIdInstConDel() {
        return this.idInstConDel;
    }
    
    public void setIdInstConDel(Integer idInstConDel) {
        this.idInstConDel = idInstConDel;
    }
    public CatInstitucion getCatInstitucion() {
        return this.catInstitucion;
    }
    
    public void setCatInstitucion(CatInstitucion catInstitucion) {
        this.catInstitucion = catInstitucion;
    }
    public CatDelegacion getCatDelegacion() {
        return this.catDelegacion;
    }
    
    public void setCatDelegacion(CatDelegacion catDelegacion) {
        this.catDelegacion = catDelegacion;
    }
    public CatContacto getCatContacto() {
        return this.catContacto;
    }
    
    public void setCatContacto(CatContacto catContacto) {
        this.catContacto = catContacto;
    }
    public CatCategoriaDenuncia getCatCategoriaDenuncia() {
        return this.catCategoriaDenuncia;
    }
    
    public void setCatCategoriaDenuncia(CatCategoriaDenuncia catCategoriaDenuncia) {
        this.catCategoriaDenuncia = catCategoriaDenuncia;
    }




}


