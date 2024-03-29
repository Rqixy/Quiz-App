package libs.json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON関連の操作をまとめたクラス
 */
public class Json {
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	/**
	 * サーブレットのレスポンスから、渡ってきたJSONを取得する
	 * @param request	リクエスト
	 * @return json		JSON
	 * @throws JsonMappingException
	 * @throws JsonProcessingException
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 */
	public static JsonNode jsonByRequest(HttpServletRequest request) throws JsonMappingException, JsonProcessingException, UnsupportedEncodingException, IOException {
		JsonNode json = null;
		try {
		    BufferedReader br = new BufferedReader(request.getReader());
		    // String型のJSONファイル取得
		    String jsonText = br.readLine();
		    jsonText = URLDecoder.decode(jsonText, "UTF-8");
		    
		    json = OBJECT_MAPPER.readTree(jsonText);
		} catch (JsonMappingException e) {
			System.out.println("JsonMappingException : " + e.getMessage());
		} catch (JsonProcessingException e) {
			System.out.println("JsonProcessingException : " + e.getMessage());
		} catch (UnsupportedEncodingException e) {
			System.out.println("UnsupportedEncodingException : " + e.getMessage());
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		} 
		return json;
	}
	
	/**
	 * フラグハッシュ配列を文字列型のJSONに変える処理
	 * @param hashMap
	 * @return strJson
	 * @throws JsonProcessingException
	 */
	public static String HashMapToString(final HashMap<?, ?> hashMap) throws JsonProcessingException {
		String strJson = "";
		
		try {
			strJson = OBJECT_MAPPER.writeValueAsString(hashMap);
		} catch (JsonProcessingException e) {
			System.out.println("JsonProcessingException : " + e.getMessage());
		}
		
		return strJson;
	}
	
	/**
	 * 文字列型JSONをフロントにレスポンスする処理
	 * @param  strJson	
	 * @param  response
	 * @throws IOException
	 */
	public static void ResponseStringJson(HttpServletResponse response, final String strJson) throws IOException {
		try {
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.print(strJson);
			pw.close();
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}
}
