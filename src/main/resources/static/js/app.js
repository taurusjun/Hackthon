var appModule = angular.module('myApp', []);

appModule.controller('orderController', function ($scope,$http) {
	
	var urlBase="";
	$scope.toggle=true;
	$scope.propertities={ORDER_ID:"Order012000142199",TENANT_ID:"Tenant012",ORDER_DATE:"2015-09-25"};
	
	$scope.orders={};
	$scope.showList=true;
	
	$scope.metaArray = [];
	$scope.names=[];
	
	$http.defaults.headers.post["Content-Type"] = "application/json";

	$scope.entity={
			objectType:"T_ORDER",
			propertities: $scope.propertities};

	$scope.showAddDiv=function showAddDiv(){
    	$scope.showList=!$scope.showList;
    }
	$scope.hideMe=function hideMe(name){
//		if(name=="ORDER_ID"){
//			return false;
//		}
		if(name=="TENANT_ID"){
			return false;
		}
		if(name=="ORDER_DATE"){
			return false;
		}
		
		return true;
    }
	
	
    function findMetas() {
        $http.post(urlBase + '/propertiesMeta/getByTenantIdAndObjectName?objectName=T_ORDER').
            success(function (data) {
                    $scope.metaArray = data;
                    for(var i=0;i<data.length;i++){
                    	$scope.names.push(data[i].displayName);
                    }
            });
    }
    
    findMetas();

    var orderReqData = {
        objectType : "T_ORDER"
    };
    function listAllOrders() {
        $http.post(urlBase + '/entities',orderReqData).
            success(function (data) {
                    $scope.orders = data;
                    $scope.showList=true;
            });
    }
    
    listAllOrders();

    $scope.addOrder = function addOrder() {
		 $http.post(urlBase + '/entity',$scope.entity).
		  success(function(data, status, headers) {
			 //alert("Task added");
			 findMetas();
			 listAllOrders();
		});
	};

    function findAllTasks() {
        //get all tasks and display initially
        $http.get(urlBase + '/customers').
            success(function (data) {
                if (data._embedded != undefined) {
                    $scope.customers = data._embedded.customers;
                } else {
                    $scope.customers = [];
                }
            });
    }

//    findAllTasks();

	//add a new task
	$scope.addTask = function addTask() {
		if($scope.taskName=="" || $scope.taskDesc=="" || $scope.taskPriority == "" || $scope.taskStatus == ""){
			alert("Insufficient Data! Please provide values for task name, description, priortiy and status");
		}
		else{
		 $http.post(urlBase + '/tasks', {
             taskName: $scope.taskName,
             taskDescription: $scope.taskDesc,
             taskPriority: $scope.taskPriority,
             taskStatus: $scope.taskStatus
         }).
		  success(function(data, status, headers) {
			 alert("Task added");
             var newTaskUri = headers()["location"];
             console.log("Might be good to GET " + newTaskUri + " and append the task.");
             // Refetching EVERYTHING every time can get expensive over time
             // Better solution would be to $http.get(headers()["location"]) and add it to the list
             findAllTasks();
		    });
		}
	};
		
	// toggle selection for a given task by task id
	  $scope.toggleSelection = function toggleSelection(taskUri) {
	    var idx = $scope.selection.indexOf(taskUri);

	    // is currently selected
        // HTTP PATCH to ACTIVE state
	    if (idx > -1) {
	      $http.patch(taskUri, { taskStatus: 'ACTIVE' }).
		  success(function(data) {
		      alert("Task unmarked");
              findAllTasks();
		    });
	      $scope.selection.splice(idx, 1);
	    }

	    // is newly selected
        // HTTP PATCH to COMPLETED state
	    else {
	      $http.patch(taskUri, { taskStatus: 'COMPLETED' }).
		  success(function(data) {
			  alert("Task marked completed");
              findAllTasks();
		    });
	      $scope.selection.push(taskUri);
	    }
	  };
	  
	
	// Archive Completed Tasks
	  $scope.archiveTasks = function archiveTasks() {
          $scope.selection.forEach(function(taskUri) {
              if (taskUri != undefined) {
                  $http.patch(taskUri, { taskArchived: 1});
              }
          });
          alert("Successfully Archived");
          console.log("It's risky to run this without confirming all the patches are done. when.js is great for that");
          findAllTasks();
	  };
	
});

appModule.controller('udfController', function ($scope,$http) {
	
	var urlBase="";
	
	$scope.ObjectNames=["T_ORDER","T_ORDER_LINE"];
	$scope.ObjectTypes=["NVARCHAR","TIMESTAMP","DECIMAL"];
	
	$scope.propertyMeta={};

	$scope.propertyMeta={};
	$scope.propertyMeta.objectName="T_ORDER";
	$scope.propertyMeta.type="NVARCHAR";
	$scope.propertyMeta.displayName="ds";
	
	$scope.metaArray = [];
	$scope.meta2Array = [];
	$http.defaults.headers.post["Content-Type"] = "application/json";

    function findMetas() {
        $http.post(urlBase + '/propertiesMeta/getByTenantIdAndObjectName?objectName=T_ORDER').
            success(function (data) {
                    $scope.metaArray = data;
            });

        $http.post(urlBase + '/propertiesMeta/getByTenantIdAndObjectName?objectName=T_ORDER_LINE').
        success(function (data) {
                $scope.meta2Array = data;
        });
        
        $scope.showList=true;
    }
    
    findMetas();
    
    $scope.showAddDiv=function showAddDiv(){
    	$scope.showList=!$scope.showList;
    }

    $scope.deleteUDF=function deleteUDF(id){
		 $http.delete(urlBase + '/propertiesMeta'+id,$scope.propertyMeta).
		  success(function(data, status, headers) {
			 //alert("Task added");
			 findMetas();
		});
    }
    
	$scope.addMeta = function addMeta() {
		 $http.post(urlBase + '/propertiesMeta',$scope.propertyMeta).
		  success(function(data, status, headers) {
			 //alert("Task added");
			 findMetas();
		});
	};
});