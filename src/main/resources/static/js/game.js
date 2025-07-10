// 게임 생성 모드 선택
function clickMode(e){
	let moveSpeeds = document.getElementsByName("moveSpeed");
	let bounceNumber = document.getElementsByName("bounceNumber");
	//let maxTargetSize = document.getElementsByName("maxTargetSize");
	
	if(e.value == "MOVING"){
		for (let i = 0; i < moveSpeeds.length; i++) {
		    moveSpeeds[i].disabled = false;
		    bounceNumber[i].disabled = false;
		    //maxTargetSize[i].disabled = true;
		}
	}else{
		for (let i = 0; i < moveSpeeds.length; i++) {
		    moveSpeeds[i].disabled = true;
		    bounceNumber[i].disabled = true;
		    //maxTargetSize[i].disabled = false;
		}
	}
}

function ableLossPoint(e){
	let lossPoint = document.getElementsByName("lossPoint");
    
    for (let i = 0; i < lossPoint.length; i++) {
		if(e.value > 0 ){
	        lossPoint[i].disabled= false;
		}else{
	        lossPoint[i].disabled= true;
		}
    }
}