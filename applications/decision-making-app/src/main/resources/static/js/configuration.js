var app = angular.module('w5app');

app.controllerProvider.register('configuration', function($scope, $http) {
	
	//////////////////////////////////////////////////////////
    /////////////////////// Globals //////////////////////////
    //////////////////////////////////////////////////////////
	var selectedRows = {};
	
	////////////////////////////////////////////////////////
	////////////////// Mock Data ///////////////////////////
	////////////////////////////////////////////////////////
	var data = new Array();
    var components =
    [
        "CDN Local", "CDN Europe", "SPR", "Video Encoder"
    ];
    var supervision =
    [
        "Enabled", "Enabled", "Enabled", "Disabled"
    ];
    
    //////////////////////////////////////////////////////////
    /////////////////// Data Preparation /////////////////////
    //////////////////////////////////////////////////////////
    for (var i = 0; i < components.length; i++) {
        var row = {};
        row["component"] = components[i];
        row["supervision"] = supervision[i];
        data[i] = row;
    }
    var source =
    {
    	datatype: "json",
		datafields: [
			{ name: 'component', type: 'string' },
			{ name: 'supervision', type: 'string' }
		],
		id: 'configurationId',
		localdata: data
    };
    var dataAdapter = new $.jqx.dataAdapter(source, {
        loadComplete: function (data) { },
        loadError: function (xhr, status, error) { }      
    });
    

    //////////////////////////////////////////////////////////
    ////////////////////// Data Grid /////////////////////////
    //////////////////////////////////////////////////////////
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
		    { text: 'Component', datafield: 'component' },
			{ text: 'Supervision', datafield: 'supervision',
				
			}
		],
		ready: function()
		{
			// Row selection event binding
			$('#jqxGrid').bind('rowselect', function(event)  {
				
				var current_index = event.args.rowindex;

				// This is a workaround for a jqxGrid bug:
				//
				// When you clear the grid selection, rowselect event is triggered instead of rowunselect.
				// Rowselect event is triggered on three types of actions:
				//	1 - Selection of a single row. event.args.rowindex is of type Number
				//  2 - Selection of all the rows. event.args.rowindex is of type Array and its elements are the row indexes
				//  3 - Clearing the selection of all the rows. event.args.rowindex is of type Array and is empty.
				//
				if (current_index instanceof Array)
				{
					var selectedRowCount = current_index.length
					if (selectedRowCount <= 0) {
						selectedRows = {};
						$('#btnEnable').jqxButton({ disabled: true });
						$('#btnDisable').jqxButton({ disabled: true });
					}
					else {
						for (var i=0; i < selectedRowCount; i++){
							var rowIndex = current_index[i];
							var datarow = $('#jqxGrid').jqxGrid('getrowdata', rowIndex);
							selectedRows[rowIndex] = datarow;
						}
						$('#btnEnable').jqxButton({ disabled: false });
						$('#btnDisable').jqxButton({ disabled: false });
					}
				}
				else {
					var datarow = $('#jqxGrid').jqxGrid('getrowdata', current_index);
					selectedRows[current_index] = datarow;
					$('#btnEnable').jqxButton({ disabled: false });
					$('#btnDisable').jqxButton({ disabled: false });
				}
			});
			
			//Row unselect event binding
			$('#jqxGrid').bind('rowunselect', function(event)  {
				var current_index = event.args.rowindex;
				var datarow = $('#jqxGrid').jqxGrid('getrowdata', current_index);
				
				delete selectedRows[current_index];
				
				if (Object.keys(selectedRows).length <= 0) {
					$('#btnEnable').jqxButton({ disabled: true });
					$('#btnDisable').jqxButton({ disabled: true });
				}
			});
		}
	};
	$scope.createWidget = true;
	
	//////////////////////////////////////////////////////////
    //////////////////// Radio & Buttons /////////////////////
    //////////////////////////////////////////////////////////
	$("#rbtnSupervised").jqxRadioButton({ width: 200, height: 25 });
	$("#rbtnAutomatic").jqxRadioButton({ width: 200, height: 25 });
	
	
	 $("#rbtnSupervised").bind('checked', function (event) {
		 $('#supervision').show();
     });
     $("#rbtnAutomatic").bind('checked', function (event) {
    	 $('#supervision').hide();
     });
     
     $('#btnEnable').jqxButton({ disabled: true });
	 $('#btnDisable').jqxButton({ disabled: true });
	 
	 $("#btnEnable").bind('click', function (event) {
		 var keys = Object.keys(selectedRows);
		 for (var i=0; i < keys.length; i++){
			 $("#jqxGrid").jqxGrid('setcellvalue', keys[i], "supervision", "Enabled");
		 }
     });
     $("#btnDisable").bind('click', function (event) {
    	 var keys = Object.keys(selectedRows);
		 for (var i=0; i < keys.length; i++){
			 $("#jqxGrid").jqxGrid('setcellvalue', keys[i], "supervision", "Disabled");
		 }
     });
	 
	 
     
 	 //////////////////////////////////////////////////////////
     //////////////////// Page Initialization /////////////////
     //////////////////////////////////////////////////////////
     //Hiding the grid and the radio buttons 
     $('#supervision').hide();
     //Automatic configuration is selected by default
     $("#rbtnAutomatic").jqxRadioButton('check');
});
