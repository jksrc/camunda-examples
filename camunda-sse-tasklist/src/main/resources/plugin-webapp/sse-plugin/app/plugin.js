define([ 'angular' ], function(angular) {

	var SseController = function($scope, $http, Uri, $location, search,
			$rootScope, $location, $timeout) {
		if (!!window.EventSource) {
			var user = $rootScope.authentication.name.valueOf();
			var source = new EventSource('/rest/server-sent-events/client/'
					+ user);

			source.addEventListener('message', function(e) {
				console.log("message: " + e.data);
				var jsonObject = JSON.parse(e.data);

				var currentPath = $location.path();
				var currentSearch = $location.search();
				if (jsonObject.type == "newtask") {
					console.log("newtask " + jsonObject.taskId);
					function delayedCallNew() {
						$rootScope.$apply(function() {
							currentSearch.task = jsonObject.taskId;
							$location.path(currentPath).search(currentSearch);
						});
					}

					window.setTimeout(delayedCallNew, 80);
				}

				if (jsonObject.type == "cleartask") {
					console.log("cleartask " + jsonObject.task)
					function delayedCallClear() {
						$rootScope.$apply(function() {
							currentSearch.task = "";
							$location.path(currentPath).search(currentSearch);
						});
					}

					window.setTimeout(delayedCallClear, 80);
				}

			}, false);

			source.addEventListener('open', function(e) {
				console.log('SSE Connection was opened.');
			}, false);

			source.addEventListener('error', function(e) {
				if (e.readyState == EventSource.CLOSED) {
					console.log('SSE Connection was closed.');
				}
			}, false);
		} else {
			console.log("no sse event source");
		}
	};

	var Configuration = [ 'ViewsProvider', function(ViewsProvider) {

		ViewsProvider.registerDefaultView('tasklist.list', {
			id : 'sse-plugin',
			label : 'Header',
			url : 'plugin://sse-plugin/static/app/dummy.html',
			controller : SseController,

			priority : 800
		});
	} ];

	var ngModule = angular.module('tasklist.plugin.sse-plugin', []);

	ngModule.config(Configuration);

	return ngModule;
});
