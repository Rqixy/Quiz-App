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
		// 数秒後に次の問題に遷移する
		setTimeout(() => {
			console.log('click!');
		}, 5000);
		
		const requestUrl = 'http://localhost:8080/QuizApp/AnswerServlet';

		postData(requestUrl, { selectedAnswer: answerBtn[i].value })
			.then((data) => {
				console.log(data);
			});
	}, false);
}
