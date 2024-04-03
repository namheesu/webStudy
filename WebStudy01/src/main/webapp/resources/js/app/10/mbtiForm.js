/**
 * 
 */
//1. 페이지가 완성되면, 
//2. 비동기 요청(/10/mbti)을 전송하고,
//3. json content를 수신한 후,
//4. select의 option 태그를 동적 구성함.
/*
MBTI 유형을 선택하면, 해당 MBTI 의 컨텐츠를 현재 페이지 하단에 출려하라.
1. select change 이벤트 처리 -> submit이벤트 트리거 시키고 있음
2. submit 이벤트의 고유 액션을 중단
3. 동기를 비동기로 전환하여 전송(나머지 모든 조건은 동일).
4. HTML 컨텐츠 수신
5. div 생성하고, form next sibling 으로 추가 
6. div의 children으로 수신한 HTML 컨트네츠를 줄력.
EDD(Event Driven Development), TDD(Test Driven Development)
	
*/
// WWD plugin

//function resolve(){	// 함수선언식
//	
//}
//var resolve = function(){
//	
//};
var fnResolveEntrySet = (jsonObj)=>{	// 함수표현식
	let mapArray = jsonObj.entrySet;
	let options = "";
	
	options += mapArray.map((associativeArray)=>{	
		console.log(associativeArray);
		let option = "";
		for(let propName in associativeArray) {		// 1번 실행
			let entryKey = propName;
			let entryValue = associativeArray[entryKey];
			option += `<option value="${entryKey}">${entryValue}</option>`;
		}
		return option;
	}).join("\n");		// 한개의 문자열로 묶어줌
	document.querySelector("[name='type']").innerHTML = options;
};

var findCookie = (name) => {
	let matches = document.cookie.match(`${name}=([^;]+)`);
	return matches ? matches[1] : undefined;
};

const select = document.querySelector("[name='type']");

var fnResolveEntryMap = (jsonObj)=>{
	
	let associativeArray = jsonObj.entryMap;
	let options = "";
	for(let propName in associativeArray) {		// 16번 실행
		let entryKey = propName;
		let entryValue = associativeArray[entryKey];
		options += `<option value="${entryKey}">${entryValue}</option>`;
	}
	select.innerHTML = options;
	
//	let savedType = findCookie("mbtiCookie");
	let savedType = select.dataset.initValue;
	if(savedType){
		select.value = savedType;
		select.form.requestSubmit();
	}
};

var fnOptionLoad = (event)=>{
	cPath = document.body.dataset.contextPath;
	fetch(`${cPath}/10/mbti`, {
		headers : {
			"Accept" : "application/json"
		}
	}).then(resp=>{
		if(resp.ok) {
			return resp.json();
		} else {
			throw new Error(`상태코드 ${resp.status} 수신`, {cause:resp});
		}
	}).then(fnResolveEntryMap).catch(err=>console.error(err));
};
// 이벤트가 아무리 버블링 돼더라도 target은 바뀌지 않는다
var fnMbtiLoad = (event)=>{
	if(event.target.tagName !== "FORM") return false; 	// form 인지 확인
	let form = event.target;
	event.preventDefault();
	let formData = new FormData(form);	// key,value쌍으로 필요하기때문에
	let urlSearchParmas = new URLSearchParams(formData);
	fetch(`${form.action}?${urlSearchParmas}`, {
		method : form.method,
		headers : {
			"Accept" : "text/html"
		},cache:"no-cache"
	}).then(resp=>{
		if(resp.ok) {
			return resp.text();
		} else {
			throw new Error(`상태코드 ${resp.status} 수신`, {cause:resp});
		}
	}).then(html=>{
		if(!window['resultArea']){
			let div = document.createElement("div");
			div.id = "resultArea";
			form.after(div);	// form.before(), form.parentElement.append()
		}
		let parser = new DOMParser();
		let newDoc = parser.parseFromString(html, "text/html");
		let preTag = newDoc.body.innerHTML;	// children
		resultArea.innerHTML = preTag;
	}).catch(err=>console.error(err));
	
	return false;
};

document.addEventListener("DOMContentLoaded", fnOptionLoad);
document.addEventListener("submit", fnMbtiLoad);
	
