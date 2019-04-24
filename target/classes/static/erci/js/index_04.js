$(document).ready(function () {
	line_01();
})

var line_01 = function(){
	// 基于准备好的dom，初始化echarts实例
		   var myChart = echarts.init(document.getElementById('line_01'));
		   window.onresize = myChart.resize;  // 适应屏幕放大缩小
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
				   data:[]
			   }]
		   };
		   // 使用刚指定的配置项和数据显示图表。
		   myChart.setOption(option);
		//定义变量
		var safetyIndex = '';
	   //发送ajax请求发送数据
		$.ajax({
			cache:'false',
			type: "get",
			url: "/phone/phoneSafetyIndexData",//安全行为监控页请求
			success: function (data) {
				//赋值
				safetyIndex += data.safetyIndex;

				//安全指数回显
				$("#anquanzhishu").html(safetyIndex);
				$("#WorkerProgressBar").css("width",safetyIndex+"%");
				// 填入数据
				myChart.setOption({
					xAxis: {
						splitLine: {
							show: true,
							lineStyle: {
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
								fontWeight: "600",
								fontSize: "8"
							}
						},
						axisTick: {//决定是否显示坐标刻度
							alignWithLabel: false,
							show: false
						},
						axisLine: {
							lineStyle: {color: '#F2F9FF'}    // x轴坐标轴颜色
						},
						data: data.safetyIndexDateArr
					},
					series: [{
						type: 'line',
						itemStyle: {
							normal: {
								lineStyle: {
									color: '#FF2A06',
									width: "3"
								}
							}
						},
						data: data.safetyIndexArr,
						// 显示数值
						itemStyle : { normal: {label : {show: true}}}
					}]
				});
			}
		});
}