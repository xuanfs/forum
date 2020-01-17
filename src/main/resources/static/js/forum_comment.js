
function comment_question(){
    var id = $("#question_id").val();
    var content = $("#content").val();
    comment(id,1,content);
}

function comment_sub(e){
    var id = e.getAttribute("data-id");
    var content = $("#comment_sub_"+id).val();
    comment(id,2,content);
}

function comment(id,type,content,msg){
    if(content==""){
        alert("回复内容不能为空！");
        return;
    }
    $.ajax({
        type:"GET",
        url:"/comment",
        contentType:"application/json",
        data:{
            "parentId":id,
            "content":content,
            "type":type
        },
        dateType:"JSON",
        success:function (date) {
            if(date.code==200){
                window.location.reload();
                return
            }
            alert(date.msg)
        }
    })
}

function collapse_comments(e){
    var id = e.getAttribute("data-id");
    var comment = $("#comment_"+id);
    var ico = $("#comment_ico_"+id);
    if(comment.hasClass("in")){
        comment.removeClass("in");
        ico.removeClass("blue")
    }else{
        comment.addClass("in");
        ico.addClass("blue");
        $.getJSON("/subComment/"+id,function(date){
            var innerhtml='';
            if(date.code==200){
                $.each(date.date,function () {
                    innerhtml+="<div class=\"media\">"
                            +"<div class=\"media-left\">"
                                +"<a href=\"#\">"
                                +"<img class=\"media-object\"  src=\""+this.user.avatarUrl+"\">"
                                +"</a>"
                            +"</div>"
                            +"<div class=\"media-body\">"
                                +"<h5 class=\"media-heading\">"
                                +"<span>"+this.user.name+"</span>"
                                +"</h5>"
                                +"<div>"
                                +"<span>"+this.content+"</span>"
                                +"</div>"
                                +"<div class=\"menu\">"
                                    +"<span class=\"btn-publish\">"+moment(this.gmtCreate).format('YYYY-MM-DD HH:mm')+"</span>"
                                +"</div>"
                            +"</div>"
                            +"<hr>"
                            +"</div>"
                });
                $("#subComment_"+id).html(innerhtml);

            }else{
                innerhtml="<span>操作失败请稍后尝试</span>";
                $("#subComment_"+id).html(innerhtml);
            }
        })
    }
}
