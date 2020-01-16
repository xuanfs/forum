function comment(){
    var id = $("#question_id").val();
    var content = $("#content").val();
    $.ajax({
        type:"GET",
        url:"/comment",
        contentType:"application/json",
        data:{
            "parentId":id,
            "content":content,
            "type":1
        },
        dateType:"JSON",
        success:function (date) {
            if(date.code==200){
                $("#isTextarea").hide();
            }
            $("#msg").text(date.msg);
        }
    })
}