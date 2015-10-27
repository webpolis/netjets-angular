angular.module('netjets', ['ngSanitize', 'netjets.services', 'netjets.directives']).config(function(questionsSvcProvider){
	questionsSvcProvider.wsEndpoint = 'ws://answerhub.localhost/socket/';
	questionsSvcProvider.container = 7;
});
