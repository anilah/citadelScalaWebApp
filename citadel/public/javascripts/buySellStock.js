  //  var app = angular.module('app', ['ngTouch', 'ui.grid']);

  var app = angular.module('app', ['ui.grid','ui.grid.edit','MessageCenterModule','ui.grid.resizeColumns', 'ui.grid.moveColumns']);
     
    app.controller('MainCtrl', ['$scope','$http','messageCenterService', function ($scope,$http,messageCenterService) {

      $scope.addData = function() {
        var n = $scope.gridOpts.data.length + 1;
        $scope.gridOpts.data.push({
        	
        		  "Stock": "ABC",
        		   "buysell": 'Buy',
                  "Quantity": 100,
                  "Date": new Date("2015-01-23"),
                  "Currency":"",
                  "Price Local Currency": "",
                  "FX Rate": "",
                  "Total Cost / Proceeds in USD": "",
                  "Profit/Loss in USD": "",
                   "Cumulative Profit / Loss USD": ""
            
                  });
      };
      
      
    
      $scope.calprofitloss = function() {
        //if($scope.gridOpts.data.length > 0){
          var rowCount= $scope.gridOpts.data.length;
          var item=$scope.gridOpts.data[rowCount-1];
        
          
          var data=  {'Stock':item.Stock,'Buy/Sell':item["buysell"],'Quantity':item.Quantity,'Date':item.Date};
          
  	    $http.post('/buysellstocks', data).
  	    then(function(response) {
  	    	
  	    	var responsedata=response.data;
  	    
  	    	
  	    	if('fail'==responsedata.Status){
  	    		
  	    		messageCenterService.add('danger', '<Strong>'+responsedata.msg+'</strong>', { status: messageCenterService.status.next,html: true,timeout:3000});
  	    		
  	    	}
  	    	else{
  	    		
  	    	$scope.gridOpts.data.splice(rowCount-1,rowCount);
  	    	var responsedata=responsedata
  	    	responsedata.Date=new Date(responsedata.Date)
  	    	$scope.gridOpts.data.push(responsedata);
  	    	
  	       }
  	      // this callback will be called asynchronously
  	      // when the response is available
  	    }, function(response) {
  	      // called asynchronously if an error occurs
  	      // or server returns response with an error status.
  	    });
          
           
           
        //}
      };
     
      $scope.reset = function () {
    	 
    	  $http.post('/clearCache').then(function(response) {
    	    messageCenterService.add('danger', '<Strong>All previous Buy Sale Transcation has been Deleted</strong>', { status: messageCenterService.status.next,html: true,timeout:3000});
    	    }, function(response) {
    	      
    	    });
    	  
        data1 = angular.copy(origdata1);
       // data2 = angular.copy(origdata2);
     
        $scope.gridOpts.data = data1;
        $scope.gridOpts.columnDefs = columnDefs1;
      }
     
      var columnDefs1 = [
                         
      //  { name: 'id', enableCellEdit: false, width: '10%' },                 
        { name: 'Stock',editableCellTemplate: 'ui-grid/dropdownEditor',width:'10%',maxWidth: 80,headerCellClass:'red',
        	
        	 cellFilter: 'mapStock', editDropdownValueLabel: 'Stock', editDropdownOptionsArray:
             	[{ id: 'ABC', Stock: 'ABC' },{ id: 'SGX', Stock: 'SGX' },{ id: 'BNP', Stock: 'BNP' },{ id: 'Citi', Stock: 'Citi' }] 
        	
        },
        { name: 'buysell',displayName:'Buy/Sell',editableCellTemplate: 'ui-grid/dropdownEditor',maxWidth: 100,headerCellClass:'red', 
        
            cellFilter: 'mapGender', editDropdownValueLabel: 'buysell', editDropdownOptionsArray:
            	[{ id: 'Buy', buysell: 'Buy' },{ id: 'Sell', buysell: 'Sell' }] 
        },
        { name: 'Quantity',enableCellEdit: true,maxWidth: 100,headerCellClass:'red'   },
        { name: 'Date',enableCellEdit: true, type: 'date',cellFilter:'date:"yyyy-MM-dd"',maxWidth: 150,headerCellClass:'red'  },
        { name: 'Currency',enableCellEdit: false,maxWidth: 100,headerCellClass:'red'  },
        { name: 'Price Local Currency',enableCellEdit: false,maxWidth:180,headerCellClass:'red' },
        { name: 'Fx Rate',enableCellEdit: false,maxWidth: 100,headerCellClass:'red'  },
        { name: 'Total Cost / Proceeds in USD',enableCellEdit: false,displayName:'Total Cost/Proceeds in USD', maxWidth:250 ,headerCellClass:'red' },
        { name: 'Profit/Loss in USD',enableCellEdit: false,displayName:'Profit/Loss in USD', maxWidth:170,headerCellClass:'red'  },
        { name: 'Cumulative Profit / Loss USD',enableCellEdit: false,displayName:'Cumulative Profit / Loss (USD)', headerCellClass:'red' }
        
      ];
      
   
      var data1 = [
        {
      	  "Stock": "ABC",
		  "buysell": 'Buy',
         "Quantity": 100,
         "Date": new Date("2015-01-23"),
         "Currency":"",
         "Price Local Currency": "",
         "FX Rate": "",
         "Total Cost / Proceeds in USD": "",
         "Profit/Loss in USD": "",
          "Cumulative Profit / Loss USD": ""
        }
      ];
     
      var origdata1 = angular.copy(data1);
          
      $scope.gridOpts = {
        columnDefs: columnDefs1//,
      //  data: data1
      };
      
      $scope.msg = {};
      
//      $scope.gridOpts.onRegisterApi = function(gridApi){
//          //set gridApi on scope
//          $scope.gridApi = gridApi;
//          gridApi.edit.on.afterCellEdit($scope,function(rowEntity, colDef, newValue, oldValue){
//            $scope.msg.lastCellEdited = 'edited row id:' + rowEntity.id + ' Column:' + colDef.name + ' newValue:' + newValue + ' oldValue:' + oldValue ;
//            $scope.$apply();
//          });
//        };
     
      
      
      
      
    }]).filter('mapGender', function() {
    	  var genderHash = {
    			  'Buy': 'Buy',
    			    'Sell': 'Sell'
    			  };

    			  return function(input) {
    			    if (!input){
    			      return '';
    			    } else {
    			      return genderHash[input];
    			    }
    			  };
    			}).filter('mapStock', function() {
    		    	  var genderHash = {
    		    			  'ABC': 'ABC',
    		    			    'SGX': 'SGX',
    		    			    'BNP': 'BNP',
    		    			    'Citi': 'Citi'
    		    			  };

    		    			  return function(input) {
    		    			    if (!input){
    		    			      return '';
    		    			    } else {
    		    			      return genderHash[input];
    		    			    }
    		    			  };
    		    });

    

    
    
    
    app.controller("loadStock", ['$scope', '$http',function($scope,$http) {
    	
        $scope.stocks = [];
        var datapost={};
       
        $scope.init  = function() {
            var httpRequest = $http({
                method: 'POST',
                url: '/loadstocks',
                data: datapost

            }).then(function(response) {
            	   $scope.stocks = response.data;
            }, function(response) {
      	      // called asynchronously if an error occurs
      	      // or server returns response with an error status.
      	    });


        };
       
    }]); 
    
    
    
 app.controller("loadFX", ['$scope', '$http',function($scope,$http) {
    	
        $scope.currencies = [];
        var datapost={};
       
        $scope.init  = function() {
            var httpRequest = $http({
                method: 'POST',
                url: '/loadFX',
                data: datapost

            }).then(function(response) {
            	   $scope.currencies = response.data;
            }, function(response) {
      	      // called asynchronously if an error occurs
      	      // or server returns response with an error status.
      	    });


        };
       
    }]); 
    
    
    
    
    
    
    
    
    