package json;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Json {
	/**
	 * サーブレットのレスポンスから、渡ってきたJSONを取得する
	 * @param request	リクエスト
	 * @return json		JSON
	 * @throws IOException
	 */
	public JsonNode jsonByHttpServletRequest(HttpServletRequest request) throws IOException {
		JsonNode json = null;
		try {
		    BufferedReader br = new BufferedReader(request.getReader());
		    ObjectMapper ob = new ObjectMapper();
		    // String型のJSONファイル取得
		    String jsonText = br.readLine();
		    jsonText = URLDecoder.decode(jsonText, "UTF-8");
		    
		    json = ob.readTree(jsonText);
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
		return json;
	}
	
	/**
	 * checkedハッシュ配列をJSONに変換してレスポンスする
	 * @param checked
	 * @param response
	 * @throws IOException
	 */
	public void checkedMapToStrJsonAndResponse(HashMap<String, Boolean> checked, HttpServletResponse response) throws IOException {
		try {
			ObjectMapper ob = new ObjectMapper();
			// 回答結果のハッシュ配列をJSONの文字列に書き直して、表に返す
			String strChecked = ob.writeValueAsString(checked);
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.print(strChecked);
			pw.close();
		} catch (IOException e) {
			System.out.println("IOException : " + e.getMessage());
		}
	}
}
