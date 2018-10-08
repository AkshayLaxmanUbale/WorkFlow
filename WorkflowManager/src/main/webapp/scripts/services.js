app.service("newGraph", function(name,$http){
	
	$http({
		method: "POST",
		url: "/create",
		data: angular.toJson(name),
		headers : {
            'Content-Type' : 'application/json'
        }
	}).then(_success,_error);
	function _success(response){
		return response.data;
	};
	function _error(response){
		
	};
});