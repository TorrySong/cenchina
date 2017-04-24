/**
 * Created by songtao on 2017/4/20.
 */

var myApp = angular.module('myApp', ['ui.router','ngCookies']);
myApp.config(function($stateProvider, $urlRouterProvider,$locationProvider) {
    $urlRouterProvider.otherwise('/index');

    $stateProvider
        .state("index", {//首页
            url: "/index",
            views: {
                'main': {
                    templateUrl: 'html/index.html',
                    controller: 'indexController'
                }
            }
        })
        .state("prize", {//奖品
            url: "/prize",
            views: {
                'main': {
                    templateUrl: 'html/prize.html',
                    controller: 'prizeController'
                }
            }
        })

        .state("ranking", {//奖品
            url: "/ranking",
            views: {
                'main': {
                    templateUrl: 'html/ranking.html',
                    controller: 'rankingController'
                }
            }
        })

        .state("xiangqing", {//个人详情
            url: "/xiangqing",
            views: {
                'main': {
                    templateUrl: 'html/xiangqing.html',
                    controller: 'xiangqingController'
                }
            }
        })
        .state("content", {//活动内容及简介
            url: "/content",
            views: {
                'main': {
                    templateUrl: 'html/content.html',
                    controller: 'contentController'
                }
            }
        })
});
