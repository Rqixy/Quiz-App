/**
 * 回答ボタンの順番をランダムに表示させる
 */

const shuffle = ([...arr]) => {
	let m = arr.length;
	while(m) {
		const i = Math.floor(Math.random() * m--);
		[arr[m], arr[i]] = [arr[i], arr[m]];
	}

	return arr;
}

document.addEventListener('DOMContentLoaded', function() {
	const answers = document.getElementById("answers");
	const buttons = answers.getElementsByTagName("button")
	const randomButtons = shuffle(buttons);

	answers.innerHTML = '';
	randomButtons.forEach(button => {
		answers.appendChild(button);
	});
});