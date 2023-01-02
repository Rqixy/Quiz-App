/**
 * 回答ボタンの順番をランダムに表示させる
 */
document.addEventListener('DOMContentLoaded', function() {
	const answers = document.querySelectorAll("#answers");
	const buttons = answers.querySelectorAll("button")
	const randomButtons = shuffle(buttons);

	answers.innerHTML = '';
	randomButtons.forEach(button => {
		answers.appendChild(button);
	});
});

const shuffle = ([...arr]) => {
	let m = arr.length;
	while(m) {
		const i = Math.floor(Math.random() * m--);
		[arr[m], arr[i]] = [arr[i], arr[m]];
	}

	return arr;
}