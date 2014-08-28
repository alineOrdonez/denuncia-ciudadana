package com.denuncia.ciudadana.dominio;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "denuncia_config", catalog = "denuncia")
public class DenunciaConfig implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String imgPath;
	private String imgExtension;
	private int id;
	
	@Column(name = "img_path")
	public String getImgPath() {
		return imgPath;
	}
	/**
	 * @param imgPath the imgPath to set
	 */
	public void setImgPath(String imgPath) {
		this.imgPath = imgPath;
	}
	@Column(name = "img_extension")
	public String getImgExtension() {
		return imgExtension;
	}
	/**
	 * @param imgExtension the imgExtension to set
	 */
	public void setImgExtension(String imgExtension) {
		this.imgExtension = imgExtension;
	}
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	
	
	
}
