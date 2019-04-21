$(document).ready(function () {
	document.documentElement.style.fontSize = document.documentElement.clientWidth / 5 + 'px';
	line_03();
})

var line_03 = function(){
	 // 基于准备好的dom，初始化echarts实例
	        var myChart = echarts.init(document.getElementById('line_03'));
			window.onresize = myChart.resize;  // 适应屏幕放大缩小
	        // 指定图表的配置项和数据
	        var option = {
				title:{
					text:'安全事故数',
					x:'center',
					y:'50',
					textStyle: {
						color: "#82A7C0",
						fontSize:"14"
					}
				},
				tooltip: {},
	            legend: {
	                data:[]
	            },
				 grid:{
                    x:40,
                    y:100,
                    x2:20,
                    y2:60,
                    borderWidth:1
                },
	            xAxis: {
					splitLine:{
						show:true,
						lineStyle:{
							color: ['#FFFFFF'],
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
					   lineStyle:{color:'#FFFFFF'}
					},
	                data: []
	            },
	            yAxis: {
					splitLine:{
						show:true,
						lineStyle:{
							color: ['#FFFFFF'],
							width: 2,
							type: 'solid'
						}
					},
					axisLabel: {
						interval: 0,
						margin: 10,
						textStyle: {
							color: "#8B8B8B"
						}
					},
					axisLine:{
					   lineStyle:{color:'#FFFFFF'}
					}
				},
	            series: [{
	                type: 'bar',
					barWidth : 50,
					itemStyle : {  
						normal : {  
							color:'#D7ECFB',
							borderWidth:'1',
							borderColor:'#5EB5EF'
						}  
					},
					data:[],
	            }]
	        };

	        // 使用刚指定的配置项和数据显示图表。
	        myChart.setOption(option);
	//定义变量
	var gongshi = '';
	var zuigao = '';
    //书写ajax请求，然后动态的在此显示横纵坐标
    $.ajax({
        type:"get",
        url:"/phone/phoneSafetyData",//安全数据页请求
        success:function(result){
        	//变量赋值
			gongshi += "<div>"+result.safetyTime+"</div>";
			zuigao += "<div>"+result.safetyTimeBig+"</div>";
			console.log(result.safetyTime);

			//回显
			$("#huixian").html(gongshi);
			$("#huixian2").html(zuigao);
            //在此回显数据
            myChart.setOption({
                xAxis: {
                    splitLine:{
                        show:true,
                        lineStyle:{
                            color: ['#FFFFFF'],
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
                        lineStyle:{color:'#FFFFFF'}
                    },
                    data: result.date
                },
                series: [{
                    type: 'bar',
                    barWidth : 50,
                    itemStyle : {
                        normal : {
                            color:'#D7ECFB',
                            borderWidth:'1',
                            borderColor:'#5EB5EF'
                        }
                    },
                    data: result.accidentNum,
					itemStyle: {
						normal: {
							label: {
								show: true, //开启显示
								position: 'top', //在上方显示
								textStyle: { //数值样式
									color: 'black',
									fontSize: 16
								}
							}
						}
					},
                }]
            });
        }
    });
}

