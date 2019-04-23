$(document).ready(function () {
	line_01();
	line_02();
	line_03();
	line_04();
	line_05();
	load();
});


var myChart1;
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
				   data: []
			   }]
		   };
		   // 使用刚指定的配置项和数据显示图表。
		   myChart1.setOption(option);
}

var myChart2;
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
				   data: []
			   }]
		   };
		   // 使用刚指定的配置项和数据显示图表。
		   myChart2.setOption(option);
}

var myChart3;
var line_03 = function(){
	// 基于准备好的dom，初始化echarts实例
		   myChart3 = echarts.init(document.getElementById('line_03'));
		   window.onresize = myChart3.resize;  // 适应屏幕放大缩小
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
				   data: []
			   }]
		   };
		   // 使用刚指定的配置项和数据显示图表。
		   myChart3.setOption(option);
}

var myChart4;
var line_04 = function(){
   // 基于准备好的dom，初始化echarts实例
		  myChart4 = echarts.init(document.getElementById('line_04'));
		  window.onresize = myChart4.resize;  // 适应屏幕放大缩小
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
				  data: []
			  }]
		  };
		  // 使用刚指定的配置项和数据显示图表。
		  myChart4.setOption(option);
}

var myChart5;
var line_05 = function(){
   // 基于准备好的dom，初始化echarts实例
		  myChart5 = echarts.init(document.getElementById('line_05'));
		  window.onresize = myChart5.resize;  // 适应屏幕放大缩小
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
				  data: [5, 20, 26, 10, 4, 20]
			  }]
		  };
		  // 使用刚指定的配置项和数据显示图表。
		  myChart5.setOption(option);

}

function load() {
	//定义对象
	var totalData = {
		carWarning:"",
		craneWeight:"",
		dateString:"",
		dustWarning:"",
		lifterWeight:"",
		noiseWarning:"",
	}
	$.ajax({
		type:'get',
		url:'/phone/phoneSafetyStatusLine',
		success : function (data) {
			totalData.carWarning = data.carWarning,
			totalData.craneWeight = data.craneWeight,
			totalData.dateString = data.dateString,
			totalData.dustWarning = data.dustWarning,
			totalData.lifterWeight = data.lifterWeight,
			totalData.noiseWarning = data.noiseWarning,



			//插入mycharts5
			myChart5.setOption({
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
					data: totalData.dateString
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
					data: totalData.noiseWarning,
					// 显示数值
					itemStyle : { normal: {label : {show: true}}}
				}]
			});

			//设置myChart4
			myChart4.setOption({
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
					data: totalData.dateString
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
					data: totalData.carWarning,
					// 显示数值
					itemStyle : { normal: {label : {show: true}}}
				}]
			});

			//设置chart3
			myChart3.setOption({
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
					data: totalData.dateString
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
					data: totalData.dustWarning,
					// 显示数值
					itemStyle : { normal: {label : {show: true}}}
				}]
			});

			//设置chart2
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
					data: totalData.dateString
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
					data: totalData.lifterWeight,
					// 显示数值
					itemStyle : { normal: {label : {show: true}}}
				}]
			});

			//设置chart1
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
					data: totalData.dateString
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
					data: totalData.craneWeight,
					// 显示数值
					itemStyle : { normal: {label : {show: true}}}
				}]
			});
		}
	});
}