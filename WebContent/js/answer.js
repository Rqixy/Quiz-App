/**
 * 回答ボタンがクリックされた時に非同期で答えを表示し、次のページに遷移する処理
 */

const answerButtons = document.querySelectorAll(`button[name='answer']`);
for (let answerButton of answerButtons) {
	answerButton.addEventListener('click', () => {
		// 連射防止
		disabledButton(answerButtons);
		
		// 判定結果を取得
		const requestUrl = 'http://localhost:8080/QuizApp/AnswerServlet';
		postData(requestUrl, { selectedAnswer: answerButton.textContent })
		.then((data) => {
			// 受け取った結果にtrueがあったら、攻撃の画像を表示
			if (data['isCorrect']) {
				const question2 = document.querySelector("#question-2");
				createImageElement("./img/correct.png", 200, question2);
			}
			
			// 結果が帰ってきたら、回答ボタンの上に○と×の画像を表示
			for (let i = 0; i < answerButtons.length; i++) {
				if (i === 0) {
					createImageElement("./img/mark_maru.png", 100, answerButtons[i]);
					continue;
				}
				createImageElement("./img/mark_batsu.png", 100, answerButtons[i]);
			}
			
			// 次の問題へ
			nextPage(data['isFinished']);
		}).catch((error) => {
			console.log('Fetch API Error : ', error);
		});
	}, false);
}

// ボタンを1度しか押させない処理
const disabledButton = (buttons = null) => {
	for (let i = 0; i < buttons.length; i++) {
		buttons[i].disabled = true;
	}
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

// 次の問題に遷移する処理
const nextPage = (finishedQuiz = false) => {
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
	}, 3000);
}
