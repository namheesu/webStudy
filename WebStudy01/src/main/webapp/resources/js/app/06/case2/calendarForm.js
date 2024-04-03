/**
 * 
 */
	calForm.querySelectorAll("[name]").forEach((el, idx)=>{
		/*data-init-value 속성으로부터 엘리머트 초기값 로딩*/
		/*data -* 속성의 키 값 규칙성, 카멜 표기법을 표현할때 '-'으로 대체함.
		ex) data-role : key(role)
			data-init-value : key(initValue)
		*/
		let name = el.name;
		calForm[name].value =  el.dataset.initValue;
	});
	calForm.addEventListener("change", (event)=>{
		console.log(event);
		event.target.form.requestSubmit();	
	});
	
// 	$(selector).on("click",function(){})	// 정정 앨리먼트
// 	$(parent).on("click", selector, function(){})	// 동적 앨리먼트 : 이벤트 버블링 구조 활용
	
//	document.querySelectorAll(".control-a").forEach((el,idx)=>{
		document.addEventListener("click", (event)=>{
			if(event.target.classList.contains("control-a")){	// target이 a태그인지 확인
				console.log(event.target);
				let el = event.target;
				calForm.year.value = el.dataset.year;
				calForm.month.value = el.dataset.month;
				calForm.requestSubmit();
				
			}			
		});
// 	});
	
// 	event => submit이벤트
// 	fetch함수 사용
// line에는 url과 method필요
// 	=> url과 method 하드코딩 하지않음

// form의 enc타입?
		
	// 요청에 관한 부분
	calForm.addEventListener("submit", (event)=>{
		event.preventDefault();
		let url = event.target.action;		
		let method = event.target.method;
		let formData = new FormData(calForm);		// 이벤트의 타겟 혹은 칼폼 넘겨줌		==> name:value
		let urlSearchParams = new URLSearchParams(formData);		// formData와 함께 예쁘게 포장
		let fetchPromise = fetch(url, {				// url 링크
			method : method,
			headers : {
				"Content-Type" : calForm.enctype	// enctype이 accept와 비슷
			}, body : urlSearchParams				// body에 쿼리스트링 존재	 	
		});
		
		fetchPromise.then(resp=>{
			if(resp.ok) {
				return resp.text();
			} else { // 에러발생시킬 수 있어 throw
				throw new Error(`상태 코드 \${resp.status}이'; 응답으로 전송됨`, {cause:resp});		// 한번 이스케이프문자로 \사용해줘야함, cause -> 에러발생 이유
			}
		}).then(html=>{			// html을 파라미터로 받을수 있음
			calArea.innerHTML = html;
		}).catch(err=>{			//reject함수
			console.log(err);
			console.log(err.cause);
		}).finally(()=>{		//complete함수
			
		});						
	});
	
	calForm.requestSubmit();