$(".answer-submit-btn").click(addAnswer);

$(".qna-comment-slipp-articles").on("click", ".answer-delete-btn", deleteAnswer);
/*$(".answer-delete-btn").click(deleteAnswer);*/

function addAnswer(e) {
	e.preventDefault();

	console.log("addAnswer Test");

	var queryString = $(".submit-write").serialize();
	console.log(queryString);

	var url = $(".submit-write").attr("action");
	console.log(url);

	$.ajax({
		type : 'post',
		url : url,
		data : queryString,
		error : function() {
			console.log("error");
		},
		success : function(data) {
			console.log(data);

			var answerTemplate = $("#answerTemplate").html();
			var template = answerTemplate.format(data.writer.name,
					data.regDate, data.contents, data.question.id, data.id);
			$(".qna-comment-slipp-articles").prepend(template);
			$("textarea[name=contents]").val("");
		}
	})
}

function deleteAnswer(e) {
	e.preventDefault();
	
	var deleteBtn = $(this);

	var url = deleteBtn.parents().attr("action");
	
	console.log(url);

	$.ajax({
		type : 'delete',
		url : url,
		error : function() {
			console.log("error");
		},
		success : function(data) {
			console.log(data);
			if (data.valid) {
				deleteBtn.closest("article").remove();
			} else {
				alert(data.errorMessage);
			}
		}
	});
}

String.prototype.format = function() {
	var args = arguments;
	return this.replace(/{(\d+)}/g, function(match, number) {
		return typeof args[number] != 'undefined' ? args[number] : match;
	});
};