/**
 * 
 */

/*
1. document 완성 이벤트 처리
2. 비동기 요청 
3. json content 수신
4. ul 태그 구성
*/
const cPath = document.body.dataset.contextPath;
const baseURI = `${cPath}/09/property`;

function fnCommFetch(url, options, fnResolve){
	fetch(url, options)
	.then(resp=>{
		if(resp.ok) {
			return resp.json();
		} else{
			throw new Error(`상태코드 ${resp.status} 수신`, {cause:resp});
		}
	}).then(fnResolve)
		.catch(err=>console.error(err));	// 돌아온 json데이터 가지고 난 후 
}

var fnRefreshDataLiTags = jsonObj=>{	
	let keyArray = jsonObj.keySet;  
	let ulTag = document.createElement("ul");	// 객체
	let liTags = keyArray.map(k=>`
		<li class="list-group-item">
			<span class="delBtn">삭제</span>
			<span class="property-name" data-bs-toggle="modal" data-bs-target="#exampleModal">${k}</span>
		</li>
	`).join("\n")
	ulTag.innerHTML = liTags;
	ulTag.classList.add("list-group");
	document.body.append(ulTag);
};

var fnReadProperties = (event)=>{
	fnCommFetch(baseURI, {
		headers : {
			"Accept" :  "application/json"
		}
	}, fnRefreshDataLiTags);
};

var fnReadProperty = (event) => {
	if (!event.relatedTarget.classList.contains("property-name")) return false;

	let propertyNameTag = event.relatedTarget;
	let propertyName = propertyNameTag.innerHTML;
	let modal = event.target;

	fnCommFetch(`${baseURI}/${propertyName}`, {
		headers: {
			"Accept": "application/json"
		}
	}, jsonObj => {
		let property = jsonObj.property;
		for(let n in property){
			updateForm[n].value = property[n];
		}
//		updateForm.propertyName.value = property.propertyName;
//		updateForm.propertyValue.value = property.propertyValue;
//		updateForm.description.value = property.description;
	});
	document.querySelector("li.active")?.classList.toggle("active")	// 토글링하다
	propertyNameTag.parentElement.classList.toggle("active");
}

const formToObject = (form)=>{
	let data = {};
	
	let formData = new FormData(form);
	
	for(let n of formData.keys()){
		data[n] = formData.get(n);
	}
	
	return data;
};

var fnAddProperty = (event)=>{
	event.preventDefault();
	let form = event.target;
	
	let data = formToObject(form);
	
	let body = JSON.stringify(data);	// 마샬링으로 변환
	fnCommFetch(baseURI, {
		method : "post",
		headers : {
			"Accept" : "application/json",	// 주고받는 데이터의 형식이 같다 
			"Content-Type" : "application/json"
		}, body : body
	}, jsonObj=>{	// resolve함수
		document.querySelector("ul").remove();
		fnRefreshDataLiTags(jsonObj);
		form.reset();
	});
	return false;
};

var fnRemoveProperty = (event)=>{
	if(!event.target.classList.contains("delBtn")) return false;	// 포함하고 있는 target인지 확인. 없으면 false
	let propertyNameTag = event.target.nextElementSibling;
	let propertyName = propertyNameTag.innerHTML;
	
	var input = confirm(`${propertyName} 정말로 지우시겠습니까?`);
	if(!input) return false;
	
	fnCommFetch(`${baseURI}/${propertyName}`, {
		method : "delete",
		headers : {
			"accept" : "application/json"
		}
	}, jsonObj=>{
//		jsonObj.success
		if(jsonObj.success){
			propertyNameTag.parentElement.remove();	// 부모태그인 li를 삭제
		}
	});
};

var fnModifyProperty = (event)=>{
	event.preventDefault();
	let form = event.target;
	
	let data = formToObject(form);
	
	let body = JSON.stringify(data);
	fnCommFetch(`${baseURI}/${data.propertyName}`, {
		method : "put",
		headers : {
			"Accept" : "application/json",
			"Content-Type" : "application/json"
		}, body : body
	}, jsonObj=>{	// resolve함수
		if(jsonObj.success) {
			let modalInstance = bootstrap.Modal.getInstance(exampleModal);
			modalInstance.hide();
			let modalToggler = document.querySelector("li.active").children[1];
			setTimeout(()=>{
				modalInstance.show(modalToggler);
			}, 1000)
		}
	});
	return false;
};


document.addEventListener("DOMContentLoaded", fnReadProperties);	// 함수 레퍼런스

document.addEventListener("show.bs.modal", fnReadProperty);

document.addEventListener("hidden.bs.modal", (event)=>{
	event.target.querySelector("form")?.reset();
});
	
insertForm.addEventListener("submit", fnAddProperty);	

updateForm.addEventListener("submit", fnModifyProperty);

document.addEventListener("click", fnRemoveProperty);
