package com.denuncia.ciudadana.controlador;

import java.math.BigDecimal;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
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

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public @ResponseBody ResultadoDTO getInitConfig(ModelMap model) {
		ResultadoDTO rs = fachada.loadInitialConfig();
		return rs;
	}

	@RequestMapping(value = "/list/{json}",headers = "Accept=application/json")
	public ListadoDenunciasDTO getIncidencias(@PathVariable String json) {
		JSONParser parser=new JSONParser();
		try {
			JSONObject jsonObj=(JSONObject) parser.parse(json);
			String ic = (String) jsonObj.get("ic");
			String dc = (String) jsonObj.get("dc");
			String la = (String) jsonObj.get("la");
			String lo = (String) jsonObj.get("lo");
			BigDecimal latitud = new BigDecimal(la);
			BigDecimal longitud = new BigDecimal(lo);
			ListadoDenunciasDTO resultado = fachada.findNear(latitud, longitud, dc, ic);
			return resultado;
		} catch (ParseException e) {
			return null;
		}
	}
	
	
	@RequestMapping(value = "/incidencia/{json}",headers = "Accept=application/json")
	public ResultadoAltaIncidenciaDTO saveIncidencia(@PathVariable String json) {
		JSONParser parser=new JSONParser();
		try {
			JSONObject jsonObj=(JSONObject) parser.parse(json);
			String ic = (String) jsonObj.get("ic");
			int icInt = Integer.parseInt(ic);
			String dc = (String) jsonObj.get("dc");
			String la = (String) jsonObj.get("la");
			String lo = (String) jsonObj.get("lo");
			String em = (String) jsonObj.get("em");
			String im = (String) jsonObj.get("im");
			String dd = (String) jsonObj.get("dd");
			BigDecimal latitud = new BigDecimal(la);
			BigDecimal longitud = new BigDecimal(lo);
			int pk = fachada.save(icInt, dd, em, im, latitud, longitud);
			if(pk > 0){
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
	
	@RequestMapping(value = "/eventualidad/{json}",headers = "Accept=application/json")
	public ListadoDenunciasDTO seleccionaEventualidad(@PathVariable String json) {
		JSONParser parser=new JSONParser();
		try {
			JSONObject jsonObj=(JSONObject) parser.parse(json);
			String ic = (String) jsonObj.get("ic");
			String i = (String) jsonObj.get("i");
			String em = (String) jsonObj.get("em");
			String id = (String) jsonObj.get("id");
			int idInteger = Integer.parseInt(id);
			boolean b = fachada.aumentaContador(idInteger);
			if(b){
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

}