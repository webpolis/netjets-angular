angular.module('netjets.directives', ['netjets.services']).directive('questions', ['questionsSvc', function(questionsSvc) {
    return {
        restrict: 'E',
        transclude: true,
        replace: true,
        scope: {
            space: '=',
            page: '=',
            pageSize: '='
        },
        templateUrl: 'src/directives/templates/questions.html',
        controller: ['$scope', 'questionsSvc', function($scope, questionsSvc) {
            $scope.questions = [];

            var update = function(questions) {
                $scope.questions = questions;
            };

            questionsSvc.listBySpace($scope.space, $scope.page, $scope.pageSize).then(update);

            // watch pagination
            $scope.$watchGroup(['page', 'pageSize'], function(n, o) {
                if (!angular.equals(n, o)) {
                    questionsSvc.listBySpace($scope.space, $scope.page, $scope.pageSize).then(update);
                }
            });
        }]
    };
}]);
