/**
 *
 */

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

const answerBtn = document.querySelectorAll(`button[type='submit'][name='answer']`);
for (let i = 0; i < answerBtn.length; i++) {
	answerBtn[i].addEventListener('click', function() {
		const requestUrl = 'http://localhost:8080/QuizApp/AnswerServlet';
		postData(requestUrl, { selectedAnswer: answerBtn[i].value })
			.then((data) => {
				// 受け取った結果にtrueがあったら、攻撃の画像を表示する
				if (data['checkAnswer']) {
					const correctImage = document.createElement('img');
					correctImage.src = "./img/correct.png";
					const question2 = document.querySelector("#question-2");
					question2.appendChild(correctImage);
				}

				// 結果が帰ってきたら、回答ボタンの上に○と×の画像を表示する
				const markMaru = document.createElement('img');
				markMaru.src = "./img/mark_maru.png";
				answerBtn[0].appendChild(markMaru);
				const markBatsu1 = document.createElement('img');
				markBatsu1.src = "./img/mark_batsu.png";
				answerBtn[1].appendChild(markBatsu1);
				const markBatsu2 = document.createElement('img');
				markBatsu2.src = "./img/mark_batsu.png";
				answerBtn[2].appendChild(markBatsu2);
				const markBatsu3 = document.createElement('img');
				markBatsu3.src = "./img/mark_batsu.png";
				answerBtn[3].appendChild(markBatsu3);

				setTimeout(function() {
					const form = document.createElement('form');
					form.method = 'post';
					if (data['finished']) {
						form.action = 'http://localhost:8080/QuizApp/ResultServlet';
					} else {
						form.action = 'http://localhost:8080/QuizApp/QuizServlet';
					}
					document.body.appendChild(form)
					form.submit();
				}, 3000);
			});
	}, false);
}
