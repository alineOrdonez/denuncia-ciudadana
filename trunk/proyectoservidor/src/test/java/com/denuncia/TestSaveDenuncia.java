package com.denuncia;

import java.math.BigDecimal;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import com.denuncia.ciudadana.fachada.DenunciaFachada;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/test/java/com/denuncia/testAppCtx.xml" })
@TransactionConfiguration(defaultRollback = true)
public class TestSaveDenuncia {

	private final static Logger LOGGER = LoggerFactory
			.getLogger(TestSaveDenuncia.class);
	
	@Autowired
	private DenunciaFachada fachada;
	
	@Test
	@Transactional	
	public void testImageSave(){
		String json = "{\"i\": \"03\", \"ic\": \"01\", \"dc\": \"fuga de agua\", \"em\": \"me@mail.com\", \"im\": \"/9j/4AAQSkZJRgABAQAAAQABAAD/2wBDAAoHBwgHBgoICAgLCgoLDhgQDg0NDh0VFhEYIx8lJCIfIiEmKzcvJik0KSEiMEExNDk7Pj4+JS5ESUM8SDc9Pjv/2wBDAQoLCw4NDhwQEBw7KCIoOzs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozs7Ozv/wAARCAAPAA8DASIAAhEBAxEB/8QAHwAAAQUBAQEBAQEAAAAAAAAAAAECAwQFBgcICQoL/8QAtRAAAgEDAwIEAwUFBAQAAAF9AQIDAAQRBRIhMUEGE1FhByJxFDKBkaEII0KxwRVS0fAkM2JyggkKFhcYGRolJicoKSo0NTY3ODk6Q0RFRkdISUpTVFVWV1hZWmNkZWZnaGlqc3R1dnd4eXqDhIWGh4iJipKTlJWWl5iZmqKjpKWmp6ipqrKztLW2t7i5usLDxMXGx8jJytLT1NXW19jZ2uHi4+Tl5ufo6erx8vP09fb3+Pn6/8QAHwEAAwEBAQEBAQEBAQAAAAAAAAECAwQFBgcICQoL/8QAtREAAgECBAQDBAcFBAQAAQJ3AAECAxEEBSExBhJBUQdhcRMiMoEIFEKRobHBCSMzUvAVYnLRChYkNOEl8RcYGRomJygpKjU2Nzg5OkNERUZHSElKU1RVVldYWVpjZGVmZ2hpanN0dXZ3eHl6goOEhYaHiImKkpOUlZaXmJmaoqOkpaanqKmqsrO0tba3uLm6wsPExcbHyMnK0tPU1dbX2Nna4uPk5ebn6Onq8vP09fb3+Pn6/9oADAMBAAIRAxEAPwDtYZH1S+vFku7nz0uJkSKO6liVlV2UABWABAA7c81LaTtZ+IrK0hu55fOd451kuJJVGI3bA3k8gqORjuKozaV4isNQ1H7DYCZLqZ3W4jkQMqsxYgbmBB5weO3FWNK0zXbnxHaahqVklstspLuWQmU7GUcKx5+bk8DiuZOV9up7Uo0vZt8yty7X1vbt/TP/2Q==\",  \"dd\":\"Hortaliza No. 51\",  \"la\": \"30.701383\", \"lo\": \"-9.163417\"}";
		try {
			JSONParser parser=new JSONParser();
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
			assertNotSame(0, pk);
		} catch (ParseException e) {
		}
	}
}
