/**
 * 
 */
/*
1. DOM(body를 포함) 트리 구조가 완성된 이후 실행되는 코드vv b
2. calForm submit 이벤트 중단
3. 비동기 요청 전송
	line : action, method
	header : accept, content-type
	body : form's inputs, (parameter : queryString)
*/

document.addEventListener("DOMContentLoaded", ()=>{
	calForm.addEventListener("submit", (event)=>{
		event.preventDefault();
		let form = event.target;
		let url = calForm.action;
		let method = form.method;

		let contentType = "application/json";

		let accept = "text/html";
		let formData = new FormData(form);
		let nativeData = {
			leftOp : parseFloat(formData.get("left")),
			rightOp : parseFloat(formData.get("right")),
			operatorType : formData.get("operator")
		};
		let jsonStr = JSON.stringify(nativeData);	// marshalling , 마샬러 , JSON이라는 API로 마샬링(.stringify), 언마샬링(.parse)함
		
		let fetchPromise =  fetch(url, {	// fetch사용은 promise를 사용하겠다는 의미
			method : method,
			headers : {
				"Content-Type" : contentType,		// request의 content
				"Accept" : accept
			}, body : jsonStr
		});	
		fetchPromise.then(resp=>{
			if(resp.ok) {
				return resp.text();
			} else {
				throw new Error(`상태코드 : ${resp.status} 발생`, {cause:resp});
			}
		}).then(text=>{
			resultArea.innerHTML = text;
		}).catch(err=>{
			console.log(err.message);
			if(err.cause){
				let resp = err.cause;
				resp.text().then(ep=>resultArea.innerHTML=ep);
			}
		});
		return false;
	});
});