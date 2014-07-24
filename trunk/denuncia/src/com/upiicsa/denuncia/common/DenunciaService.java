package com.upiicsa.denuncia.common;

import java.util.HashMap;

import net.sf.json.JSONObject;

public interface DenunciaService {

	public JSONObject setRequest(HashMap<String, Object> map);
}
