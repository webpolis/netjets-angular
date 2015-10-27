angular.module('netjets.services', ['ngWebSocket']).provider('questionsSvc', function() {
    this.wsEndpoint = null;
    this.container = null;
    this.socket = null;
    this.$get = ['$websocket', '$q', function($websocket, $q) {
        var _this = this;
        _this.socket = $websocket(_this.wsEndpoint);

        return {
            listBySpace: function(space, page, pageSize, sort) {
                var def = $q.defer();

                _this.socket.onMessage(function(ret) {
                    def.resolve(angular.fromJson(ret.data));
                });

                _this.socket.send({
                    action: 'listBySpace',
                    space: space,
                    sort: sort,
                    page: page,
                    pageSize: pageSize,
                    site: _this.container
                });

                return def.promise;
            }
        };
    }];
});
