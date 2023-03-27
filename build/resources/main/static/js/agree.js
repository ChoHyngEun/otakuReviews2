$(document).ready(function(){
    
    $("#nextbtn").click(function(){                         //id nextbtn 클릭했을때 
        if($("#agree1").is(":checked") == false){           //id agree1 이 체크가 안되었을경우
            alert("이용 약관(필수)에 동의 하셔야 다음 단계로 진행 가능합니다."); //alert실행
            return;                                                     //다시 위로
        }else if($("#agree2").is(":checked") == false){                 
            alert("개인정보 수집 및 이용(필수)에 동의 하셔야 다음 단계로 진행 가능합니다..");
            return;
        }else{
            $("#join").submit();
        }
    });    

});