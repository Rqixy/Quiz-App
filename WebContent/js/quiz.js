/**
 * 問題の回答を非同期で行い、その後自動で次の問題やリザルトページに遷移する
 */
 
'use strict';

let answers = document.querySelectorAll(`input[type='button'][name='answer']`);

const hoge = (e) =>{
  console.log(e.target.value)
}
 
for (var i=0; i < answers.length; i++) {
	console.log(answers[i]);
	console.log(answers[i].value);
    answers[i].addEventListener('click', hoge, false)
};