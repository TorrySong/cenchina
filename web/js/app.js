/**
 * Created by songtao on 2017/4/20.
 */

var myApp = angular.module('myApp', ['ui.router','ngCookies']);
myApp.config(function($stateProvider, $urlRouterProvider,$locationProvider) {
    $urlRouterProvider.otherwise('/index');

    $stateProvider
        .state("index", {//��ҳ
            url: "/index",
            views: {
                'main': {
                    templateUrl: 'html/index.html',
                    controller: 'indexController'
                }
            }
        })
        .state("prize", {//��Ʒ
            url: "/prize",
            views: {
                'main': {
                    templateUrl: 'html/prize.html',
                    controller: 'prizeController'
                }
            }
        })

        .state("ranking", {//��Ʒ
            url: "/prize",
            views: {
                'main': {
                    templateUrl: 'html/ranking.html',
                    controller: 'rankingController'
                }
            }
        })
});
