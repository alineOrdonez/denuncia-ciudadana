/**
 * 
 */
package com.denuncia.ciudadana.servicio.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.denuncia.ciudadana.dominio.Denuncia;
import com.denuncia.ciudadana.dominio.DenunciaConfig;
import com.denuncia.ciudadana.persistencia.PersistenciaException;
import com.denuncia.ciudadana.persistencia.dao.DenunciaDAO;
import com.denuncia.ciudadana.servicio.DenunciaConfigServicio;
import com.denuncia.ciudadana.servicio.DenunciaServicio;
import com.denuncia.ciudadana.servicio.ServicioException;

/**
 * @author amaro
 *
 */
@Service
public class DenunciaServicioImpl implements DenunciaServicio {

	@Autowired
	private DenunciaDAO daoDenuncia;
	@Autowired
	private DenunciaConfigServicio servicioConfig;
	
	@Override
	@Transactional(readOnly=true)
	public List<Denuncia> findNear(BigDecimal latitud, BigDecimal longitud) throws ServicioException{
		try{
			List<Denuncia> result = daoDenuncia.findNear(longitud, latitud);
			return result;
		}catch(PersistenciaException e){
			throw new ServicioException("Error en findNear:"+e.getMessage(), e);
		}
	}
	
	@Override
	@Transactional
	public int save(Denuncia dominio) throws ServicioException{
		try{
			int pk = daoDenuncia.save(dominio);
			return pk;
		}catch(PersistenciaException e){
			throw new ServicioException("Erro en save:"+e.getMessage(), e);
		}
	}
	
	@Override
	@Transactional
	public boolean addDenuncia(int idDenuncia) throws ServicioException{
		try{
			Denuncia dominio = daoDenuncia.findByPK(idDenuncia);
			if(dominio != null){
				int contador = dominio.getContador();
				++contador;
				dominio.setContador(contador);
				daoDenuncia.update(dominio);
				return true;
			}
			return false;
		}catch(PersistenciaException e){
			throw new ServicioException("Error en xx:"+e.getMessage(),e);
		}
	}
	
	@Override
	public String saveImage(String image) throws ServicioException{
		try{
			String sd = RandomStringUtils.randomAlphabetic(15);
			DenunciaConfig config = servicioConfig.findConfig();
			String imageName = config.getImgPath()+sd+config.getImgExtension();
			 byte[] imageByteArray = decodeImage(image);
			 File file = new File(imageName);
			 FileOutputStream imageOutFile = new FileOutputStream(
	                    file);
			 imageOutFile.write(imageByteArray);
			 imageOutFile.close();
			 return imageName;
		}catch(Exception e){
			throw new ServicioException("Error en saveImage:"+e.getMessage(), e);
		}
	}
	
	/**
     * Encodes the byte array into base64 string
     *
     * @param imageByteArray - byte array
     * @return String a {@link java.lang.String}
     */
    public static String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }
     
    /**
     * Decodes the base64 string into byte array
     *
     * @param imageDataString - a {@link java.lang.String}
     * @return byte array
     */
    public static byte[] decodeImage(String imageDataString) {
        return Base64.decodeBase64(imageDataString);
    }
}
