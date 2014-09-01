package com.denuncia.ciudadana.controlador;

import java.math.BigDecimal;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.denuncia.ciudadana.dto.ListadoDenunciasDTO;
import com.denuncia.ciudadana.dto.ResultadoAltaIncidenciaDTO;
import com.denuncia.ciudadana.dto.ResultadoDTO;
import com.denuncia.ciudadana.fachada.DenunciaFachada;
import com.denuncia.ciudadana.util.Constantes;

@RestController
@RequestMapping("/service/denuncia")
public class DenunciaController {

	@Autowired
	private DenunciaFachada fachada;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public @ResponseBody ResultadoDTO getInitConfig(ModelMap model) {
		ResultadoDTO rs = fachada.loadInitialConfig();
		return rs;
	}

	// TODO: Trae todos los registros, modificar las restricciones
	@RequestMapping(value = "/list/", method = RequestMethod.POST, headers = "Accept=application/json")
	public ListadoDenunciasDTO getIncidencias(@RequestBody String json) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObj = (JSONObject) parser.parse(json);
			String ic = String.valueOf(jsonObj.get("ic"));
			String dc = (String) jsonObj.get("dc");
			String la = String.valueOf(jsonObj.get("la"));
			String lo = String.valueOf(jsonObj.get("lo"));
			BigDecimal latitud = new BigDecimal(la);
			BigDecimal longitud = new BigDecimal(lo);
			ListadoDenunciasDTO resultado = fachada.findNear(latitud, longitud,
					dc, ic);
			return resultado;
		} catch (ParseException e) {
			return null;
		}
	}

	@RequestMapping(value = "/incidencia/", method = RequestMethod.POST, headers = "Accept=application/json")
	public ResultadoAltaIncidenciaDTO saveIncidencia(@RequestBody String json) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObj = (JSONObject) parser.parse(json);
			String ic = String.valueOf(jsonObj.get("ic"));
			int icInt = Integer.parseInt(ic);
			String dc = (String) jsonObj.get("dc");
			String ds = (String) jsonObj.get("ds");
			String la = (String) jsonObj.get("la");
			String lo = (String) jsonObj.get("lo");
			String em = (String) jsonObj.get("em");
			String im = (String) jsonObj.get("im");
			String dd = (String) jsonObj.get("dd");
			BigDecimal latitud = new BigDecimal(la);
			BigDecimal longitud = new BigDecimal(lo);
			int pk = fachada.save(icInt, ds, dd, em, im, latitud, longitud);
			if (pk > 0) {
				ResultadoAltaIncidenciaDTO dto = new ResultadoAltaIncidenciaDTO();
				dto.setDs(Constantes.DSC_ESTATUS_EXITOSO);
				dto.setIs(Constantes.ESTATUS_EXITOSO);
				return dto;
			}
			return null;
		} catch (ParseException e) {
			return null;
		}
	}

	@RequestMapping(value = "/eventualidad/", method = RequestMethod.POST, headers = "Accept=application/json")
	public ListadoDenunciasDTO seleccionaEventualidad(@RequestBody String json) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObj = (JSONObject) parser.parse(json);
			String ic = String.valueOf(jsonObj.get("ic"));
			String i = String.valueOf(jsonObj.get("i"));
			String em = (String) jsonObj.get("em");
			String id = (String) jsonObj.get("id");
			int idInteger = Integer.parseInt(id);
			boolean b = fachada.aumentaContador(idInteger, em);
			if (b) {
				ListadoDenunciasDTO resultado = new ListadoDenunciasDTO();
				resultado.setDs(Constantes.DSC_ESTATUS_EXITOSO);
				resultado.setIs(Constantes.ESTATUS_EXITOSO);
				return resultado;
			}
			return null;
		} catch (ParseException e) {
			return null;
		}
	}

	@RequestMapping(value = "/detalle/", method = RequestMethod.POST, headers = "Accept=application/json")
	public ListadoDenunciasDTO enviarDetalleDeDenuncia(@RequestBody String json) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObj = (JSONObject) parser.parse(json);
			String ic = String.valueOf(jsonObj.get("ic"));
			String id = String.valueOf(jsonObj.get("id"));
			int idDenuncia = Integer.parseInt(id);
			String imagen = fachada.getImage(idDenuncia);
			ListadoDenunciasDTO resultado = new ListadoDenunciasDTO();
			resultado.setDs(Constantes.DSC_ESTATUS_EXITOSO);
			resultado.setIs(Constantes.ESTATUS_EXITOSO);
			resultado.setIm(imagen);
			return resultado;
		} catch (ParseException e) {
			return null;
		}
	}

	// TODO: Trae todos los registros, modificar las restricciones
	@RequestMapping(value = "/consulta/", method = RequestMethod.POST, headers = "Accept=application/json")
	public ListadoDenunciasDTO getConsulta(@RequestBody String json) {
		JSONParser parser = new JSONParser();
		try {
			JSONObject jsonObj = (JSONObject) parser.parse(json);
			String ic = String.valueOf(jsonObj.get("ic"));
			String dc = (String) jsonObj.get("dc");
			String pt = String.valueOf(jsonObj.get("pt"));
			int periodo = Integer.parseInt(pt);
			String la = String.valueOf(jsonObj.get("la"));
			String lo = String.valueOf(jsonObj.get("lo"));
			BigDecimal latitud = new BigDecimal(la);
			BigDecimal longitud = new BigDecimal(lo);
			ListadoDenunciasDTO resultado = fachada.findWithPeriod(latitud,
					longitud, ic, periodo);
			return resultado;
		} catch (ParseException e) {
			return null;
		}
	}
}