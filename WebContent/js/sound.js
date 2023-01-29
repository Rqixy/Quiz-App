/**
 * クリック時にSEをつける処理
 */

// 実行
soundEffectButton(".button-se", "click",  500);
soundEffectButton(".button-logout", "logout",  500);
soundEffectButton(".button-stage", "stage",  1000);
soundEffectButton(".button-escape", "escape",  1000);

// SEをボタンにつける処理
function soundEffectButton(buttonClass = '', sound = '', clickDelay = 0) {
	let preventEvent = true;
	
	const buttons = document.querySelectorAll(buttonClass);
	for (let button of buttons) {
		button.addEventListener('click', (e) => {
			// クリックしたらSEなり終わるまで1度処理を止める
			if(preventEvent) {
				button.disabled = true;
				e.preventDefault();
				preventEvent = false;
				
				const soundEffect = new Audio(`/QuizApp/sounds/${sound}.mp3`);
				soundEffect.currentTime = 0;
				soundEffect.volume = 0.1;
				soundEffect.play();
			}
			
			setTimeout(() => {
				button.disabled = false;
				button.click();
			}, clickDelay);
		});
	}
}
