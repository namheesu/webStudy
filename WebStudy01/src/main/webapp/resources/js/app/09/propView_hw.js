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
//<script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>

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
		let propertyValue = jsonObj.propertyValue;
		updateForm.propertyName.value = propertyName;
		updateForm.propertyValue.value = propertyValue;
	});
	document.querySelector("li.active")?.classList.toggle("active")	// 토글링하다
	propertyNameTag.parentElement.classList.toggle("active");
}

var fnAddProperty = (event)=>{
	
};

var fnRemoveProperty = (event)=>{
	if(!event.target.classList.contains("delBtn")) return false;	// 포함하고 있는 target인지 확인. 없으면 false
	let propertyNameTag = event.target.nextElementSibling;
	let propertyName = propertyNameTag.innerHTML;
	
	$ajax({
		url : `${baseURI}/${propertyName}`,
		method : "delete",
		contentType : "application/json",
		success : function(jsonObj){
			if(jsonObj.success){
				$(propertyNameTag).parent().remove();
			}
		}
	});
};

$(fnModifyProperty).ready(function(event){
	event.preventDefault();
	let form = event.target;
	let data = {
		propertyName : form.propertyName.value,
		propertyValue : form.propertyValue.value
	};
	$.ajax({
		url : `${baseURI}/${data.propertyName}`,
		method : "put",
		contentType : "application/json",
		data : JSON.stringify(data),
		success : function(jsonObj) {
			if(jsonObj.success){
				let modalInstance = new bootstrap.Modal(exampleModal);
				modalInstance.hide();
				let modalToggler = $("li.active").children()[1];
				setTimeout(()=>{
				modalInstance.show(modalToggler);
				}, 1000)
			}
		}
	});
	return false;
});


$(document).on("DOMContentLoaded", fnReadProperties)

$(document).on(".show.bs.modal",fnReadProperty)

$(document).on("hidden.bs.modal",function(){
	
});
	

$("#insertForm").on("submit",fnAddProperty)

$("#updateForm").on("submit",fnModifyProperty);	

$(document).on("click", ".delBtn", fnRemoveProperty);
