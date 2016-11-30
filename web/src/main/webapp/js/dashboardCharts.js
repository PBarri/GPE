function drawAllCharts(charts) {
	if(charts.length > 0) {
		for(i = 0; i < charts.length; i++) {
			drawChart(charts[i]);
		}
	}
}
	    
function drawChart(chart) {
    
  var projectId = chart.shift();
  var chartData = chart.shift();

  drawPieChart(projectId, chartData);
  drawGanttChart(projectId, chart);
  
}

function drawPieChart(projectId, chartData) {
	var chartColors = chartData.pop();

      var data = new google.visualization.DataTable();
      data.addColumn('string', 'Element');
      data.addColumn('number', 'Percentage');
      data.addRows(chartData);
      
      var options = {
        chartArea: {
          	top:'5%', width:'100%', height:'95%'
        },
        legend: {
          	position: 'right',
          	textStyle: {
            	fontName: ["Roboto"],
            	fontSize: '13',
            	color: '#676a6c'
          	}
        },
        sliceVisibilityThreshold: 0
      };

      options["colors"] = chartColors;
        
      var emptyMsg = "#{msg['tasks.list.emptyMsg']}";
      var divId = "taskStatus_".concat(projectId);
      if (data.getFilteredRows([{column: 1, minValue: 1}]).length == 0) {				
    	  $('#'+divId).append(emptyMsg);
		} else {
			var divId = "taskStatus_" + projectId;
			var projectChart = new google.visualization.PieChart(document.getElementById(divId));
			projectChart.draw(data, options);
		}
}

function drawGanttChart(projectId, chartData) {
	var data = new google.visualization.DataTable();
	data.addColumn('string', 'ID Tarea');
	data.addColumn('string', 'Código Tarea');
	data.addColumn('string', 'Tipo de Tarea');
	data.addColumn('date', 'Fecha Inicio');
	data.addColumn('date', 'Fecha Fin');
	data.addColumn('number', 'Duración');
	data.addColumn('number', 'Porcentaje');
	data.addColumn('string', 'Dependencias');
	data.addRows(chartData[0]);
	
	 var options = {
        chartArea: {
          	top:'5%', width:'100%', height:'95%'
        },
        height: (data.getNumberOfRows() * 42) + 50,
        width: 1500,
        gantt: {
        	criticalPathEnabled: true,
		    criticalPathStyle: {
		    	stroke: '#e64a19',
		    	strokeWidth: 5
		    }
        }
	 };

	var divId = "projectGantt_".concat(projectId);
	if (data.getNumberOfRows() == 0) {
		$('#'+divId).append("No hay datos suficientes para realizar el diagrama de Gantt");
	} else {
		var chart = new google.visualization.Gantt(document.getElementById(divId));
		chart.draw(data, options);
	}			
}

function toMilliseconds(hours) {
	return hours * 60 * 60 * 1000;
}