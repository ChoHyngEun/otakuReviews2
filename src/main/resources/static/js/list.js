$(document).ready(function() {
    // 드롭다운 메뉴를 열고 닫을 아이콘을 클릭할 때마다 이벤트를 발생시킵니다.
    $(".has-dropdown > a").click(function(e) {
        e.preventDefault();
        $(this).parent().toggleClass("active");
        $(this).next("ul").slideToggle(500);
        //드롭다운 내려오고 올라갈때 걸리는 시간
    });
});