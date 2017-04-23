
myApp.controller('indexController', function($scope, $http, $state,$cookieStore,$stateParams) {
    var uid = 123;
    $scope.sid =1467272246637 ;
    var pages = 2;
    var stv = setInterval(function(){
        $scope.docuHgt = parseInt($(document).height());
        $scope.winHgt =  parseInt($(window).height());
        $scope.scrTop = parseInt($(document).scrollTop());
        if($scope.scrTop>=$scope.docuHgt-$scope.winHgt-10){
            $('.loadingBot').show();
            getMore(pages*5);
            pages++;
        }
    },500)
    getMore('5');
    function getMore(pages) {
        $http.get("http://localhost:8090/controller1/first",{cache:true}).success(function(data){
            $scope.info = data;
            $scope.total= data.length;
            console.log($scope.info);
            pages/5==Math.ceil($scope.total/5)?$('.loadingBot').hide():$('.loadingBot').fadeOut();
            if(pages/5>Math.ceil($scope.total/5)){
                clearInterval(stv)
            }
        });
    }

    $http.get("https://api.duwu.mobi/duwu/vp/storeHome.json?u="+uid+"&storeid="+$scope.sid+"&start=0&size="+pages+"&test=1")
        .success(function(response) {
            $scope.data = response.attachment;
            $scope.total = response.attachment.total;
        })
});


myApp.controller('prizeController', function($scope, $http, $state,$cookieStore,$stateParams) {

})

myApp.controller('rankingController', function($scope, $http, $state,$cookieStore,$stateParams) {
    $http.get("http://localhost:8090/controller1/first",{cache:true}).success(function(data){
        $scope.info = data;
    });
})

myApp.controller('xiangqingController', function($scope, $http, $state,$cookieStore,$stateParams) {

})