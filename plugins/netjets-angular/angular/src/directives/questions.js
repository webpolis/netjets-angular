angular.module('netjets.directives', ['netjets.services']).directive('questions', ['$rootScope', 'questionsSvc', function($rootScope, questionsSvc) {
    var isUpdateEventSubscribed = false;

    return {
        restrict: 'E',
        transclude: true,
        replace: true,
        scope: {
            space: '=',
            page: '=',
            pageSize: '=',
            sort: '='
        },
        templateUrl: 'src/directives/templates/questions.html',
        controller: ['$scope', '$timeout', '$interval', 'questionsSvc', function($scope, $timeout, $interval, questionsSvc) {
            $scope.questions = [];
            $scope.pagination = null;

            var update = function() {
                questionsSvc.listBySpace($scope.space, $scope.page, $scope.pageSize, $scope.sort);
            };

            // subscribe to update event
            $rootScope.$on('update', function(ev, msg) {
                var data = angular.fromJson(msg.data);

                if (data.update) {
                    $timeout(function() {
                        questionsSvc.listBySpace($scope.space, $scope.page, $scope.pageSize, $scope.sort);
                    }, 1000);
                } else if (angular.isArray(data.list)) {
                    $scope.questions = data.list;
                    delete data.list;
                    $scope.pagination = data;
                }
            });

            // watch pagination
            $scope.$watchGroup(['space', 'page', 'pageSize', 'sort'], function(n, o) {
                if (!angular.equals(n, o)) {
                    questionsSvc.listBySpace($scope.space, $scope.page, $scope.pageSize, $scope.sort);
                }
            });

            // init list
            questionsSvc.listBySpace($scope.space, $scope.page, $scope.pageSize, $scope.sort);
        }]
    };
}]);
