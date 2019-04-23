$(document).ready(function () {
	line_01();
	line_02();
	line_03();
	line_04();
	line_05();
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
	                data: ["12/19","12/20","12/21","12/22","12/23","12/24"]
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
					data: [5, 20, 36, 10, 10, 20]
	            }]
	        };
	        // 使用刚指定的配置项和数据显示图表。
	        myChart.setOption(option);
}


var line_02 = function(){
	 // 基于准备好的dom，初始化echarts实例
	        var myChart = echarts.init(document.getElementById('line_02'));
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
	                data: ["12/19","12/20","12/21","12/22","12/23","12/24"]
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
					data: [5, 20, 36, 10, 10, 20]
	            }]
	        };
	        // 使用刚指定的配置项和数据显示图表。
	        myChart.setOption(option);
}


var line_03 = function(){
	 // 基于准备好的dom，初始化echarts实例
	        var myChart = echarts.init(document.getElementById('line_03'));
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
	                data: ["12/19","12/20","12/21","12/22","12/23","12/24"]
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
					data: [5, 20, 36, 10, 10, 20]
	            }]
	        };
	        // 使用刚指定的配置项和数据显示图表。
	        myChart.setOption(option);
}

var line_04 = function(){
	// 基于准备好的dom，初始化echarts实例
		   var myChart = echarts.init(document.getElementById('line_04'));
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
				   data: ["12/19","12/20","12/21","12/22","12/23","12/24"]
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
				   data: [5, 20, 36, 10, 10, 20]
			   }]
		   };
		   // 使用刚指定的配置项和数据显示图表。
		   myChart.setOption(option);
}

var line_05 = function(){
	// 基于准备好的dom，初始化echarts实例
		   var myChart = echarts.init(document.getElementById('line_05'));
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
				   data: ["12/19","12/20","12/21","12/22","12/23","12/24"]
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
				   data: [5, 20, 36, 10, 10, 20]
			   }]
		   };
		   // 使用刚指定的配置项和数据显示图表。
		   myChart.setOption(option);
}