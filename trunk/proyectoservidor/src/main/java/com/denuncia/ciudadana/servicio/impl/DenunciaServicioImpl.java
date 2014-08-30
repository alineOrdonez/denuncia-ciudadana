/**
 * 
 */
package com.denuncia.ciudadana.servicio.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
 * @author Aline Ordoñez
 *
 */
@Service
public class DenunciaServicioImpl implements DenunciaServicio {

	@Autowired
	private DenunciaDAO daoDenuncia;
	@Autowired
	private DenunciaConfigServicio servicioConfig;

	@Override
	@Transactional(readOnly = true)
	public List<Denuncia> findNear(BigDecimal latitud, BigDecimal longitud)
			throws ServicioException {
		try {
			// List<Denuncia> result = daoDenuncia.findNear(longitud, latitud);
			List<Denuncia> result = daoDenuncia.getAll();
			return result;
		} catch (PersistenciaException e) {
			throw new ServicioException("Error en findNear:" + e.getMessage(),
					e);
		}
	}

	@Override
	@Transactional
	public int save(Denuncia dominio) throws ServicioException {
		try {
			int pk = daoDenuncia.save(dominio);
			return pk;
		} catch (PersistenciaException e) {
			throw new ServicioException("Erro en save:" + e.getMessage(), e);
		}
	}

	@Override
	@Transactional
	public boolean addDenuncia(Denuncia denuncia) throws ServicioException {
		try {
			int id = denuncia.getIdDenuncia();
			Denuncia dominio = daoDenuncia.findByPK(id);
			if (dominio != null) {
				int contador = dominio.getContador();
				++contador;
				dominio.setContador(contador);
				dominio.setFechaHoraActCont(new Date());
				dominio.setEmailUsuario(denuncia.getEmailUsuario());
				daoDenuncia.update(dominio);
				return true;
			}
			return false;
		} catch (PersistenciaException e) {
			throw new ServicioException("Error en xx:" + e.getMessage(), e);
		}
	}

	@Override
	public String saveImage(String image) throws ServicioException {
		try {
			String sd = RandomStringUtils.randomAlphabetic(15);
			DenunciaConfig config = servicioConfig.findConfig();
			String imageName = config.getImgPath() + sd
					+ config.getImgExtension();
			byte[] imageByteArray = decodeImage(image);
			File file = new File(imageName);
			FileOutputStream imageOutFile = new FileOutputStream(file);
			imageOutFile.write(imageByteArray);
			imageOutFile.close();
			return imageName;
		} catch (Exception e) {
			throw new ServicioException("Error en saveImage:" + e.getMessage(),
					e);
		}
	}

	@Override
	@Transactional
	public String getImage(int idDenuncia) throws ServicioException {
		try {
			String path = daoDenuncia.getImagePath(idDenuncia);
			File file = new File(path);
			FileInputStream imageInFile = new FileInputStream(file);
			byte imageData[] = new byte[(int) file.length()];
			imageInFile.read(imageData);
			String imageDataString = encodeImage(imageData);
			imageInFile.close();
			return imageDataString;
		} catch (Exception e) {
			throw new ServicioException("Error en getImage:" + e.getMessage(),
					e);
		}
	}

	@Override
	@Transactional
	public List<Denuncia> findByDateAndLocation(BigDecimal latitud,
			BigDecimal longitud, int pt) throws ServicioException {
		try {
			DateTimeFormatter formatter = DateTimeFormatter
					.ofPattern("yyyy-MM-dd HH:mm:ss");
			LocalDateTime dateTime = LocalDateTime.now().minusHours(pt);
			String formattedDateTime = dateTime.format(formatter);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date dateFormatted = sdf.parse(formattedDateTime);
			List<Denuncia> result = daoDenuncia.findByDateAndLocation(longitud,
					latitud, dateFormatted);
			return result;
		} catch (Exception e) {
			throw new ServicioException("Error en findByDateAndLocation:"
					+ e.getMessage(), e);
		}
	}

	/**
	 * Encodes the byte array into base64 string
	 *
	 * @param imageByteArray
	 *            - byte array
	 * @return String a {@link java.lang.String}
	 */
	public static String encodeImage(byte[] imageByteArray) {
		return Base64.encodeBase64URLSafeString(imageByteArray);
	}

	/**
	 * Decodes the base64 string into byte array
	 *
	 * @param imageDataString
	 *            - a {@link java.lang.String}
	 * @return byte array
	 */
	public static byte[] decodeImage(String imageDataString) {
		return Base64.decodeBase64(imageDataString);
	}
}
