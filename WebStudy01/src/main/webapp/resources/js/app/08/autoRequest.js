/**
 * 
 */
/*DOMContentLoaded 이벤트핸들러*/
document.addEventListener("DOMContentLoaded", () => {	// 페이지가 랜더링 되면 바로 비동기 요청 보낸다
	setInterval(() => {
		fetch("serverTime.do", {	//메소드 생략하면 get방식 
			headers: {
				"Accept": "application/json"	// response의 contentType과일치
			}
		}).then(resp => {	// 괄호에 리절브함수
			return resp.json();	// promise객체 반환
		}).then(jsonObj => timeArea.innerHTML = jsonObj.now);
	}, 1000);

});