angular.module('netjets.directives', ['netjets.services']).directive('questions', ['questionsSvc', function(questionsSvc) {
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
        controller: ['$scope', 'questionsSvc', function($scope, questionsSvc) {
            $scope.questions = [];
            $scope.pagination = null;

            var update = function(ret) {
                $scope.questions = ret.list;
                delete ret.list;
                $scope.pagination = ret;
            };

            questionsSvc.listBySpace($scope.space, $scope.page, $scope.pageSize, $scope.sort).then(update);

            // watch pagination
            $scope.$watchGroup(['page', 'pageSize', 'sort'], function(n, o) {
                if (!angular.equals(n, o)) {
                    questionsSvc.listBySpace($scope.space, $scope.page, $scope.pageSize, $scope.sort).then(update);
                }
            });
        }]
    };
}]);
