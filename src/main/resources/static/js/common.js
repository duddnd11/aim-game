// input text, range 값 싱크 맞추기
function syncValues(e) {
    let el = document.getElementsByName(e.name);
    
    for (let i = 0; i < el.length; i++) {
        el[i].value = e.value;
    }
}

// 즐겨찾기
function heartGame(event,gameId){
	const eventDiv = event.currentTarget;
	fetch('/rest/heart/'+gameId, {
		method: 'PUT'
	})
	.then(response => {
		if (!response.ok) {
			//throw new Error(response);
			return response.json().then(errorData => {
		     	// 에러 응답 데이터에서 메시지를 확인
		      	throw new Error(errorData.message);
		    });
		}
		return response.json(); // JSON 형식으로 응답 처리
	})
	.then(data => {
		if(data.useYn == "Y"){
			eventDiv.classList.add("heartY");
		}else{
			eventDiv.classList.remove("heartY");
		}
	})
	.catch(error => {
		console.error(error); // 오류 처리
	});
}

// 프로필 이미지 클릭
function profileImgInput(){
    document.getElementById('profileImg').click();
}

// 프로필 이미지 섬네일
function profileImgChange(e){
    const file = e.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById('thumnail').src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
}

// 에러메시지 삭제
function removeErrorMsg(){
	let errorDivs = document.querySelectorAll(".error-text");
	errorDivs.forEach(function(div) {
	    div.style.display = "none";
	});
}

// 검증 에러메시지
function validationErrorMsg(error){
	for(let i=0; i<error.length; i++){
		document.getElementById(error[i].field+"Error").style.display="block";
		document.getElementById(error[i].field+"Error").textContent = error[i].message;
	}
}

// 열기 닫기 버튼 변경
function switchArrow(e){
	if(e.style.transform == "scaleY(-1)"){
		e.style.transform = "scaleY(1)";
	}else{
		e.style.transform = "scaleY(-1)";
	}
}