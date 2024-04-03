/**
 * 
 */
/*
1. DOM(body를 포함) 트리 구조가 완성된 이후 실행되는 코드
2. calForm submit 이벤트 중단                                                                                                      
3. 비동기 요청 전송
	line : action, method
	header : accept, content-type
	body : form's inputs, (parameter : queryString)
		
*/

// case1 : parameter 전송 후 HTML 응답 수신		공통점 ㅣ 파라미터를 보냄 , 편지의 내용은 바뀌지 않음 , 컨텐츠타입도 바뀌지 않음	
// case2 : parameter 전송 후 JSON 응답 수신			/ 받아오려하는 부분이 달라짐 => accept

let fnOwner = {
	fnHtml : function(html){
		resultArea.innerHTML = html;
	},
	fnJson : function(jsonObj){
		resultArea.innerHTML = jsonObj.calculator.expression;
	}
}
document.addEventListener("DOMContentLoaded", ()=>{
	calForm.addEventListener("submit", (event)=>{
		event.preventDefault();		// 페이지 새로고침 방지
		
		let form = event.target;
		let url = form.action;
		let method = form.method;
		let contentType = form.enctype;		// content-Type : 고정
		
		// name속성이 "accept"이고 checked된 상태의 요소를 선택!
		let acceptRdo = document.querySelector("[name='accept']:checked");
		
		let accept = acceptRdo?.value ?? "text/html";	// 1		--> ?? : null병합 연산자 /  .? : Optional Chaining 
		let fnName = acceptRdo?.dataset.fnName ?? "fnHtml";
		
		let formData = new FormData(form);		// 폼 데이터를 담은 객체
		let urlSearchParams = new URLSearchParams(formData);	//URLSearchParams 객체로 변환
		
		let fetchPromise =  fetch(url, {	// fetch사용은 promise를 사용하겠다는 의미
			method : method,
			headers : {
				"Content-Type" : contentType,		// request의 content
				"Accept" : accept
			}, body : urlSearchParams
		});	
		fetchPromise.then(resp=>{		// then이후의 리절브함수가 달라짐
			if(resp.ok) {	// 2
				let respContentType = resp.headers.get("Content-Type");		// response header, 위의 accept랑 동일
				if(respContentType.indexOf("json") > 0) {
					return resp.json();		// 내부적으로 언마샬링 해주는 코드
				} else {
					return resp.text();
				}
			} else {
				throw new Error(`상태코드 : ${resp.status} 발생`, {cause:resp});
			}
		}).then(fnOwner[fnName]).catch(err=>{
			console.error(err.message);
			if(err.cause){
				let resp = err.cause;
				resp.text().then(ep=>resultArea.innerHTML=ep);
			}
		});
		return false;
	});
});