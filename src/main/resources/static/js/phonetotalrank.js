$(document).ready(function () {
	document.documentElement.style.fontSize = document.documentElement.clientWidth / 5 + 'px';
	phoneSelectDate();
	getListData(1);
	BtnClickQuery();
});

function getListData(pageNo) {
    $.ajax({
        type: 'post',
        url: '/phone/queryPageFirst?pageSize=20&pageNo='+ pageNo +'', // ajax请求路径
        dataType: 'json',
        success: function (data) {
			
            if (data.totalno >= pageNo) {
				$("#ListPage").show();
				$(".tooltipList").hide();
				let tab_list = "";
					count = data.totalsize;
					limit = data.pagesize;
					datacurr = pageNo;
				$.each(data.datas, function (key, position) {
					let number = Number((pageNo-1)*20+key)+1;
				    tab_list += '<tr id='+ position.itemNo +'>' +
				        '<td><div class="itenname">' + position.itemName + '</div></td>' +
				        '<td>' + position.score + '</td>' +
				        '<td>' + number + '</td>' +
				        '</tr>'
				})
				$("#tab_list").html(tab_list);
				paged(count,limit,datacurr);
				$(".table td").bind("click", function (){
					window.location = 'details?itemNo='+$(this).parent("tr").attr("id")+'&queryDate='+ $("#dateList_Num").val();
				})
            } else{
            	// $("#tab_list").html("抱歉！数据为空");
				$("#tab_list").html("");
				$("#ListPage").hide();
				$(".tooltipList").show();
            }

        },
        error: function () {
            alert("数据请求失败！")
        }

    });
}

function paged(count,limit,datacurr) {
    layui.use(['laypage', 'layer'], function () {
		let laypage = layui.laypage,
            layer = layui.layer;
        laypage.render({
            elem: 'ListPage',
            count: count, //数据总数
			limit: limit,
			groups: 2,
			curr:datacurr,
			jump: function (obj, first) {
				currPage = obj.curr; //这里是后台返回给前端的当前页数
                if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr  ajax 再次请求
					getListData(currPage);
                }
            },

        });
    })
}

function pagedTWO(count,limit,datacurr) {
	layui.use(['laypage', 'layer'], function () {
		let laypage = layui.laypage,
			layer = layui.layer;
		laypage.render({
			elem: 'ListPage',
			count: count, //数据总数
			limit: limit,
			groups: 2,
			curr:datacurr,
			jump: function (obj, first) {
				currPage = obj.curr; //这里是后台返回给前端的当前页数
				if (!first) { //点击跳页触发函数自身，并传递当前页：obj.curr  ajax 再次请求
					queryPageByCondition(currPage);
				}
			},

		});
	})
}


// 渲染下拉框
function phoneSelectDate(){
	$.ajax({
	    type: 'get',
	    url: '/phone/phoneSelectDate', // ajax请求路径
	    dataType: 'json',
	    success: function (data) {
			let dateList_Num = "";
			$.each(data,function(key,value){
				dateList_Num += "<option value="+ value +">"+ value +"</option>"
			})
			$("#dateList_Num").append(dateList_Num);
	    },
	    error: function () {
	        alert("数据请求失败！")
	    }
	});
}


// 查询
function BtnClickQuery(){
	$('.query').click(function(){
		queryPageByCondition(1);
	})
}

// 条件筛选
function queryPageByCondition(pageNo){
		$.ajax({
	    type: 'post',
	    url: '/phone/queryPageByCondition?pageSize=20&pageNo='+ pageNo +'&queryDate='+ $("#dateList_Num").val() +'&queryWord='+ $(".input-clear").val() +'', // ajax请求路径
	    dataType: 'json',
	    success: function (data) {
	    	$("#ListPage").show();
			$(".tooltipList").hide();
			if (data.totalno >= data.pageno) {
				let tab_list = "";
					count = data.totalsize;
					limit = data.pagesize;
					datacurr = pageNo;
				$.each(data.datas, function (key, position) {
					//let queryDate = position.statisticsDate;
					//let number = Number((pageNo-1)*20+key)+1;
					tab_list += '<tr id='+ position.itemNo +'>' +
						'<td><div class="itenname">' + position.itemName + '</div></td>' +
						'<td>' + position.score + '</td>' +
						'<td>' + position.rankNumT+ '</td>' +
						'</tr>'
				})
				$("#tab_list").html(tab_list);
				pagedTWO(count,limit,datacurr);
				$(".table td").bind("click", function (){
					window.location = 'details?itemNo='+$(this).parent("tr").attr("id")+'&queryDate='+ $("#dateList_Num").val();
				})
			} else{
				// $("#tab_list").html("抱歉！数据为空");
				$("#tab_list").html("");
				$("#ListPage").hide();
				$(".tooltipList").show();
			}
	    },
	    error: function () {
	        alert("数据请求失败！")
	    }
	});
}


