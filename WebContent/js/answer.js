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
				const weapon =  randomWeapons()
				createImageElement(`./img/weapons/${weapon}.png`, 'weapon', field);
				enemy.classList.add("damage");
				attackSoundEffect(weapon);
			} else {
				// 不正解画像を表示
				createImageElement("./img/incorrect.png", 'correct-img', question);
				// 間違えたら、敵からの攻撃アニメーションとプレイヤーへのダメージアニメーションを追加
				enemy.classList.add("enemy-attack");
				player.classList.add("damage");
				attackSoundEffect("damage");
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
			// エラーが出たら、ホームに突き返す
			createFormElement('GET', 'home');
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

// 武器をランダムに選ぶ処理
const randomWeapons = () => {
	const weapons = ['tsue', 'sword', 'ono'];
	const random = Math.floor(Math.random() * weapons.length);
	return weapons[random];
}

const attackSoundEffect = (sound = '') => {
	const soundEffect = new Audio(`/QuizApp/sounds/attack_sound/${sound}.mp3`);
	
	setTimeout(() => {
		soundEffect.currentTime = 0;
		soundEffect.volume = 0.1;
		soundEffect.play();
	}, 500);
}

// 次の問題に遷移する処理
const nextPage = (finishedQuiz = false) => {
	setTimeout(() => {
		let action = '';
		if (finishedQuiz) {
			action = 'result';
		} else {
			action = 'quiz';
		}
		
		// CSRFトークン取得
		const inputCsrfTokenElement = document.querySelector(`input[name='csrf_token']`);
		// 遷移するform要素作成
		createFormElement('POST', action, inputCsrfTokenElement);
	}, 2000);
}

// imgエレメントを作成する処理
const createImageElement = (imageUrl = '', className = '', parentElement = null) => {
	const imageElement = document.createElement('img');
	imageElement.src = imageUrl;
	imageElement.className = className
	parentElement.appendChild(imageElement);
}

// form要素を作成する処理
const createFormElement = (method = '', action = '', element = null) => {
	const form = document.createElement('form');
	form.method = method;
	form.action = 'http://localhost:8080/QuizApp/' + action;
	
	form.appendChild(element);
	document.body.appendChild(form);
	form.submit();
}

