var app = angular.module('w5app');

app.controllerProvider.register('list_adaptations', function($scope, $http) {
	
//    $scope.adaptations = "";
//    $scope.getAdaptations = function() {
//        $http({
//            url: "decision-making-app/adaptation",
//            method: 'GET'
//        }).success(function(data) {
//            $scope.adaptations = data;//.firstName + " " + data.lastName;
//	            }).error(function(err) {
//	                console.log(err);
//	            });
//    };
//    $scope.getAdaptations();
	
	$http({
		url: "decision-making-app/adaptation",
		method: 'GET'
	}).success(function (data, status) {
		var localData = [];
		
		for(var i = 0; i < data.length; i++)
		{
			var row = {};
			row['name'] = data[i]['name'];
			row['description'] = data[i]['description'];
			row['priority'] = data[i]['priority'];
			
			localData.push(row);
		}
		
		// prepare the data
		var source =
		{
			datatype: "json",
			datafields: [
				{ name: 'name', type: 'string' },
				{ name: 'description', type: 'string' },
				{ name: 'priority', type: 'string' }
			],
			id: 'adaptationId',
			localdata: localData
		};
		var dataAdapter = new $.jqx.dataAdapter(source);
		
		$scope.gridSettings =
		{
			width: '100%',
			pageable: true,
			autoheight: true,
			autorowheight: true,
			source: dataAdapter,
			columnsresize: true,
			selectionmode: 'checkbox',
			columns: [
			    { text: 'Name', datafield: 'name' },
				{ text: 'Description', datafield: 'description' },
				{ text: 'Priority', datafield: 'priority', 
					cellsRenderer: function (row, columnDataField, value) {
						var tmp = value.split(";");
						var r = '<div class="jqx-grid-cell-left-align" style="margin-top: 4px; margin-bottom: 4px;">';
						for(var x = 0; x < tmp.length; x++)
						{
							r = r.concat(tmp[x]);
							if(x < tmp.length - 1)
							{
								r = r.concat("<br/>");
							}
						}
						return r.concat("</div>");
					}
				}
			],
			ready: function()
			{
				$('#btnEnact').jqxButton({ disabled: true });
				$('#btnDelete').jqxButton({ disabled: true });
				
				var selectedRows = {};
				
				$('#jqxGrid').bind('rowselect', function(event)  {
					var current_index = event.args.rowindex;
					var datarow = $('#jqxGrid').jqxGrid('getrowdata', current_index);
					
					selectedRows[current_index] = datarow;
					$('#btnEnact').jqxButton({ disabled: false });
					$('#btnDelete').jqxButton({ disabled: false });
				});
				$('#jqxGrid').bind('rowunselect', function(event)  {
					var current_index = event.args.rowindex;
					var datarow = $('#jqxGrid').jqxGrid('getrowdata', current_index);
					
					delete selectedRows[current_index];
					
					if (Object.keys(selectedRows).length <= 0) {
						$('#btnEnact').jqxButton({ disabled: true });
						$('#btnDelete').jqxButton({ disabled: true });
					}
					
				});
			}
		};
		$scope.createWidget = true;
	 }).error(function (data, status) {
		 alert(status);
	 });
});
