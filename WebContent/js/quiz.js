/**
 * 回答ボタンがクリックされた時に答えを表示し、次のページに遷移する処理
 */

const answerBtn = document.querySelectorAll(`button[type='submit'][name='answer']`);
for (let i = 0; i < answerBtn.length; i++) {
	answerBtn[i].addEventListener('click', () => {
		const requestUrl = 'http://localhost:8080/QuizApp/AnswerServlet';
		postData(requestUrl, { selectedAnswer: answerBtn[i].value })
		.then((data) => {
			// 受け取った結果にtrueがあったら、攻撃の画像を表示する
			if (data['checkedAnswer']) {
				const question2 = document.querySelector("#question-2");
				createImageElement("./img/correct.png", 200, question2);
			}
	
			// 結果が帰ってきたら、回答ボタンの上に○と×の画像を表示する
			createImageElement("./img/mark_maru.png", 100, answerBtn[0]);
			createImageElement("./img/mark_batsu.png", 100, answerBtn[1]);
			createImageElement("./img/mark_batsu.png", 100, answerBtn[2]);
			createImageElement("./img/mark_batsu.png", 100, answerBtn[3]);
			
			// 連射防止
			disabledButton(answerBtn);
			// 次も問題へ
			nextQuestion(data['finished']);
		}).catch((error) => {
			console.log('Fetch API Error : ', error);
		});
	}, false);
}

// 非同期通信でサーブレットに送る設定
async function postData(url = '', data = {}) {
	const response = await fetch(url, {
		method: 'POST',
	    mode: 'cors',
	    cache: 'no-cache',
	    credentials: 'same-origin',
	    headers: {
	      'Content-Type': 'application/json'
	    },
		redirect: 'follow',
		referrerPolicy: 'no-referrer',
		body: JSON.stringify(data)
	});

	return response.json();
}

// imgエレメントを作成する処理
const createImageElement = (imageUrl = '', width = 0, element = null) => {
	const imageElement = document.createElement('img');
	imageElement.src = imageUrl;
	imageElement.width = width;
	element.appendChild(imageElement);
}

// ボタンを1度しか押させない処理
const disabledButton = (buttons = null) => {
	for (let i = 0; i < buttons.length; i++) {
		buttons[i].disabled = true;
	}
}

// 次の問題に遷移する処理
const nextQuestion = (finishedQuiz = null) => {
	setTimeout(() => {
		const form = document.createElement('form');
		form.method = 'post';
		form.action = 'http://localhost:8080/QuizApp/';
		if (finishedQuiz) {
			form.action += 'ResultServlet';
		} else {
			form.action += 'QuizServlet';
		}
		document.body.appendChild(form);
		form.submit();
	}, 1000);
}
