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
			const question = document.querySelector("#question");
			if (data['isCorrect']) {
				// 攻撃画像を追加する
				createImageElement("./img/correct.png", 'correct-img', question);
				// モンスターにダメージを与える
				const monster = document.querySelector("#monster-img");
				addDamageClass(monster);
			} else {
				createImageElement("./img/incorrect.png", 'correct-img', question);
				// 間違えたらプレイヤーにダメージを与える
				const player = document.querySelector("#quiz");
				addDamageClass(player);
			}
			
			// 結果が帰ってきたら、回答ボタンの上に○と×の画像を表示
			for (let i = 0; i < answerButtons.length; i++) {
				if (i === 0) {
					createImageElement("./img/maru.png", 'mark-maru', answerButtons[i]);
					continue;
				}
				createImageElement("./img/batsu.png", 'mark-batsu', answerButtons[i]);
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
const createImageElement = (imageUrl = '', className = '', element = null) => {
	const imageElement = document.createElement('img');
	imageElement.src = imageUrl;
	imageElement.className = className
	element.appendChild(imageElement);
}

// ダメージクラスを追加する処理
const addDamageClass = (element = null) => {
	element.classList.add("damage");
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
	}, 2000);
}
