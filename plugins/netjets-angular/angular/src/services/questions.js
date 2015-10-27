angular.module('netjets.services', ['ngWebSocket']).provider('questionsSvc', function() {
    this.wsEndpoint = null;
    this.container = null;
    this.socket = null;
    this.$get = ['$rootScope', '$websocket', function($rootScope, $websocket) {
        var _this = this;
        _this.socket = $websocket(_this.wsEndpoint);

        _this.socket.onMessage(function(ret) {
            $rootScope.$broadcast('update', ret);
        });

        return {
            getSocket: function() {
                return _this.socket;
            },
            listBySpace: function(space, page, pageSize, sort) {
                _this.socket.send({
                    action: 'listBySpace',
                    space: space,
                    sort: sort,
                    page: page,
                    pageSize: pageSize,
                    site: _this.container
                });
            }
        };
    }];
});
