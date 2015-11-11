angular.module('netjets', ['ngSanitize', 'netjets.services', 'netjets.directives']).config(function(questionsSvcProvider){
	questionsSvcProvider.wsEndpoint = 'wss://test1.cloud.answerhub.com:443/api/socket/';
	questionsSvcProvider.container = 7;
});
