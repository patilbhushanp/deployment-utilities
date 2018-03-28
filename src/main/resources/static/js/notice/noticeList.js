$(document).ready(function(){
	getNoticeList();
});

function getNoticeList() {
	$('#noticeListTable').DataTable({
		"ajax" : '/getNoticeList',
		"columns" : [ {
			"data" : "meetingDate",
			"width" : "10%"
		}, {
			"data" : "meetingAudience",
			"width" : "15%"
		}, {
			"data" : "subject",
			"width" : "40%"
		}, {
			"data" : "contactPerson",
			"width" : "15%"
		}, {
			"data" : "meetingAcceptedCount",
			"width" : "15%"
		}, {
			"data": null,
            "defaultContent": "<a href='/noticeDetails'><i class='fa fa-bars'></i></a>",
			"width" : "5%"
		} ]
	});
}