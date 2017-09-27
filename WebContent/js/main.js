window.onbeforeunload = function(event) {
	document.getElementsByClassName('onUnload')[0].click();
	return null;
};

angular.module('NadeFacilAdminApp', ['ngMaterial', 'ui.bootstrap']).controller('AdminCtrl', function($scope) {
	$scope.addAction = function(event) {
		angular.element(".add-action-" + angular.element(event.target).closest(".add-card").attr("data")).click();
	}
});