package servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

import model.AnswerCheck;

@WebServlet("/AnswerServlet")
public class AnswerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			// 回答を取得する
			
			//. JSON テキストを全部取り出す
		    BufferedReader br = new BufferedReader( request.getReader() );
		    String jsonText = br.readLine();
		    jsonText = URLDecoder.decode(jsonText, "UTF-8");
		    
		    ObjectMapper mapper = new ObjectMapper(); 
		    
		    AnswerCheck answerCheckObj = mapper.readValue(jsonText, AnswerCheck.class);
		    
		    String selectedAnswer = answerCheckObj.getSelectedAnswer(); 
//		    System.out.println(selectedAnswer);
		    
			// quiz.jspにanswerCheckのデータをjson形式で送信する
			
			/* セッションスコープの準備 */
			HttpSession session = request.getSession();

			/* 問題リストと回答リストを取得 */
//			ArrayList<QuizInfoBean> quizList = (ArrayList<QuizInfoBean>)session.getAttribute("quizList");
//			ArrayList<AnswersBean> answerList = (ArrayList<AnswersBean>)session.getAttribute("answerList");
//
//			/* 問題番号を取得 */
//			int quizNumber = (int)session.getAttribute("quizNumber");
//
//			/* 問題リストと回答リストから出題された問題番号の要素を削除する */
//			quizList.remove(quizNumber);
//			answerList.remove(quizNumber);
//
//			// セッションスコープへオブジェクト(quizList)を保存
//			session.setAttribute("quizList", quizList);

			// 問題の答えを取得
			String quizAnswer = (String)session.getAttribute("quizAnswer");
			// ユーザーが選択した答えを取得
			// 正解数のセッションを取得
			int answerCount = (int)session.getAttribute("answerCount");

			/* 問題の答えと選択した答えが一致しているか判定 */
			// 正解か不正解か判定する変数(初期値：false)
			boolean answerCheck = false;
			if(quizAnswer.equals(selectedAnswer)) {
				// 一致していたらtrueに変更する
				answerCheck = true;

				// 正答数を+1してセッションに再保存する
				answerCount++;
				answerCheckObj.setCheckAnswer(true);
				session.setAttribute("answerCount", answerCount);
			}

			String answerCheckJson = mapper.writeValueAsString(answerCheckObj);
			response.setContentType("application/json;charset=UTF-8");
			PrintWriter pw = response.getWriter();
			pw.print(answerCheckJson);
			pw.close();
			
			// セッションにtrueかfalseを保存する
			session.setAttribute("answerCheck", answerCheck);

			
			
			// 転送処理(フォワード)
//			// 問題画面へ表示
//			ServletContext s = request.getServletContext();
//			RequestDispatcher rd = s.getRequestDispatcher("/WEB-INF/view/quiz.jsp");
//			rd.forward(request, response);
		} catch(Exception e) {
			// 例外処理
			e.getStackTrace();
		}
	}
}
