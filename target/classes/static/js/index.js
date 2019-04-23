$(document).ready(function () {
	document.documentElement.style.fontSize = document.documentElement.clientWidth / 5 + 'px';
	line_01();
	line_02();
	load();
});


var myChart1
var line_01 = function(){
	 // 基于准备好的dom，初始化echarts实例
	        myChart1 = echarts.init(document.getElementById('line_01'));
			window.onresize = myChart1.resize;  // 适应屏幕放大缩小
	        // 指定图表的配置项和数据
	        var option = {
				tooltip: {},
	            legend: {
	                data:[]
	            },
				 grid:{
	                    x:40,
	                    y:40,
	                    x2:20,
	                    y2:60,
	                    borderWidth:1
	                },
	            xAxis: {
					splitLine:{
						show:true,
						lineStyle:{
							color: ['#F2F9FF'],
							width: 2,
							type: 'solid'
						}
					},
					axisLabel: {
						interval: 0,
						// rotate: 45, //倾斜度 -90 至 90 默认为0
						margin: 10,
						textStyle: {
							color: "#000000",
							fontWeight:"600",
							fontSize:"8"
						}						
					},
					axisTick: {//决定是否显示坐标刻度  
						alignWithLabel: false,
						show:false
					},
					axisLine:{
					   lineStyle:{color:'#F2F9FF'}    // x轴坐标轴颜色
					},
	                data: []
	            },
	            yAxis: {
					splitLine:{
						show:true,
						lineStyle:{
							color: ['#F2F9FF'],
							width: 2,
							type: 'solid'
						}
					},
					axisLabel: {
						interval: 0,
						// rotate: 45, //倾斜度 -90 至 90 默认为0
						margin: 10,
						textStyle: {
							color: "#8B8B8B",
						}
					},
					axisLine:{
					   lineStyle:{color:'#F2F9FF'}    // x轴坐标轴颜色
					}
				},
	            series: [{
	                type: 'line',
					itemStyle : {  
						normal : {  
							lineStyle:{  
								color:'#FF2A06',
								width:"3"
							}  
						}  
					},
					data: [],
					// 显示数值
					itemStyle : { normal: {label : {show: true}}}
	            }]
	        };
			myChart1.hideLoading();
	        // 使用刚指定的配置项和数据显示图表。
	        myChart1.setOption(option);
}

var myChart2
var line_02 = function(){
	// 基于准备好的dom，初始化echarts实例
	myChart2 = echarts.init(document.getElementById('line_02'));
	window.onresize = myChart2.resize;  // 适应屏幕放大缩小
	// 指定图表的配置项和数据
	var option = {
		tooltip: {},
		legend: {
			data:[]
		},
		grid:{
			x:40,
			y:40,
			x2:20,
			y2:60,
			borderWidth:1
		},
		xAxis: {
			splitLine:{
				show:true,
				lineStyle:{
					color: ['#F2F9FF'],
					width: 2,
					type: 'solid'
				}
			},
			axisLabel: {
				interval: 0,
				// rotate: 45, //倾斜度 -90 至 90 默认为0
				margin: 10,
				textStyle: {
					color: "#000000",
					fontWeight:"600",
					fontSize:"8"
				}
			},
			axisTick: {//决定是否显示坐标刻度
				alignWithLabel: false,
				show:false
			},
			axisLine:{
				lineStyle:{color:'#F2F9FF'}    // x轴坐标轴颜色
			},
			data: []
		},
		yAxis: {
			splitLine:{
				show:true,
				lineStyle:{
					color: ['#F2F9FF'],
					width: 2,
					type: 'solid'
				}
			},
			axisLabel: {
				interval: 0,
				// rotate: 45, //倾斜度 -90 至 90 默认为0
				margin: 10,
				textStyle: {
					color: "#8B8B8B",
				}
			},
			axisLine:{
				lineStyle:{color:'#F2F9FF'}    // x轴坐标轴颜色
			}
		},
		series: [{
			type: 'line',
			itemStyle : {
				normal : {
					lineStyle:{
						color:'#FF2A06',
						width:"3"
					}
				}
			},
			data:[],
			// 显示数值
			// 显示数值
			itemStyle : { normal: {label : {show: true}}}
		}]
	};
	myChart2.hideLoading();
	// 使用刚指定的配置项和数据显示图表。
	myChart2.setOption(option);
}


function load() {

	var gongren = '';
	var guanli = '';

	//发送ajax请求
	$.ajax({
		type:'get',
		url:'/phone/safetyBehaviourMonData',
		success:function (data) {

			gongren += ""+data.workerTrainRate;
			guanli += ""+data.manaDutyRate;

			//回显
			$("#gongren").html(gongren);
			$("#guanli").html(guanli);

			//动态修改数据比例
			$("#WorkerProgressBar").css("width", gongren+"%");
			$("#ManagerProgressBar").css("width", guanli+"%");
			//设置折线图2
			myChart2.setOption({
				xAxis: {
					splitLine:{
						show:true,
						lineStyle:{
							color: ['#F2F9FF'],
							width: 2,
							type: 'solid'
						}
					},
					axisLabel: {
						interval: 0,
						// rotate: 45, //倾斜度 -90 至 90 默认为0
						margin: 10,
						textStyle: {
							color: "#000000",
							fontWeight:"600",
							fontSize:"8"
						}
					},
					axisTick: {//决定是否显示坐标刻度
						alignWithLabel: false,
						show:false
					},
					axisLine:{
						lineStyle:{color:'#F2F9FF'}    // x轴坐标轴颜色
					},
					data: data.dateString
				},
				series: [{
					type: 'line',
					itemStyle : {
						normal : {
							lineStyle:{
								color:'#FF2A06',
								width:"3"
							}
						}
					},
					data: data.workTrain,
					// 显示数值
					itemStyle : { normal: {label : {show: true}}}
				}]
			});
			//设置折线图1
			myChart1.setOption({
				xAxis: {
					splitLine:{
						show:true,
						lineStyle:{
							color: ['#F2F9FF'],
							width: 2,
							type: 'solid'
						}
					},
					axisLabel: {
						interval: 0,
						// rotate: 45, //倾斜度 -90 至 90 默认为0
						margin: 10,
						textStyle: {
							color: "#000000",
							fontWeight:"600",
							fontSize:"8"
						}
					},
					axisTick: {//决定是否显示坐标刻度
						alignWithLabel: false,
						show:false
					},
					axisLine:{
						lineStyle:{color:'#F2F9FF'}    // x轴坐标轴颜色
					},
					data: data.dateString
				},
				series: [{
					type: 'line',
					itemStyle : {
						normal : {
							lineStyle:{
								color:'#FF2A06',
								width:"3"
							}
						}
					},
					data:data.manaDuty,
					// 显示数值
					itemStyle : { normal: {label : {show: true}}}
				}]
			});
		}
	});
}



