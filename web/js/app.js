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
            url: "/ranking",
            views: {
                'main': {
                    templateUrl: 'html/ranking.html',
                    controller: 'rankingController'
                }
            }
        })

        .state("xiangqing", {//��������
            url: "/xiangqing",
            views: {
                'main': {
                    templateUrl: 'html/xiangqing.html',
                    controller: 'xiangqingController'
                }
            }
        })
        .state("content", {//����ݼ����
            url: "/content",
            views: {
                'main': {
                    templateUrl: 'html/content.html',
                    controller: 'contentController'
                }
            }
        })
});
