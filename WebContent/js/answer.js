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
		const requestData = { selectedAnswer: answerButton.textContent };

		postData(requestUrl, requestData).then((responseData) => {
			const question = document.querySelector("#question");
			const field = document.querySelector("#field");
			const enemy = document.querySelector("#enemy");
			const player = document.querySelector("#quiz");
			
			// 正誤判定時の画像表示とアニメーション
			if (responseData['isCorrect']) {
				// 正解画像を表示
				createImageElement("./img/correct.png", 'correct-img', question);
				// 敵に攻撃するアニメーションとダメージアニメーションを追加
				createImageElement(`./img/weapons/${randomWeapons()}.png`, 'weapon', field);
				addClass(enemy, "damage");
			} else {
				// 不正解画像を表示
				createImageElement("./img/incorrect.png", 'correct-img', question);
				// 間違えたら、敵からの攻撃アニメーションとプレイヤーへのダメージアニメーションを追加
				addClass(enemy, "enemy-attack");
				addClass(player, "damage");
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
			nextPage(responseData['isFinished']);
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
const createImageElement = (imageUrl = '', className = '', parentElement = null) => {
	const imageElement = document.createElement('img');
	imageElement.src = imageUrl;
	imageElement.className = className
	parentElement.appendChild(imageElement);
}

// クラスを追加する処理
const addClass = (element = null, className = '') => {
	element.classList.add(className);
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

// 武器をランダムに選ぶ処理
const randomWeapons = () => {
	const weapons = ['tsue', 'sword', 'ono'];
	const random = Math.floor(Math.random() * weapons.length);
	return weapons[random];
}
