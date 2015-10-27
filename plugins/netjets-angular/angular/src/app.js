angular.module('netjets', ['ngSanitize', 'netjets.services', 'netjets.directives']).config(function(questionsSvcProvider){
	questionsSvcProvider.wsEndpoint = 'ws://test1.cloud.answerhub.com/api/socket/';
	questionsSvcProvider.container = 7;
});
