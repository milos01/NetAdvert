/**
 * Created by milosandric on 23/12/2016.
 */
(function(angular){
    app.controller('home', function ($rootScope, $location, $scope, _, $log, UsersResource, AdvertResource, PictureResource, RealestateResource) {
        $scope.rentVal = true;
        $scope.saleVal = true;
        $scope.slider = {
            min: 0,
            max: 1000000,
            options: {
                floor: 0,
                ceil: 1000000,
                noSwitching: true,
                translate: function(value) {
                    return '$' + value;
                },
                onEnd: function () {
                    $scope.searchByPrice();
                }
            }
        };
        $scope.test={};
        $scope.slider2 = {
            min: 0,
            max: 1000,
            options2: {
                floor: 0,
                ceil: 1000,
                noSwitching: true,
                translate: function(value) {
                    return value + "m2";
                },
                onEnd: function () {
                    $scope.searchByArea();
                }
            }
        };

        if(!$rootScope.authenticated){
            $location.path("/");
        }

        RealestateResource.getTechEquipment().then(function (items) {
            $scope.equipment = items;
        });
        
        $scope.eqFunc = function (chName) {
            switch(chName.equipmentName) {
                case "AC":
                    airconditionChanged();
                    break;
                case "Fridge":
                    fridgeChanged();
                    break;
                case "Internet":
                    internetChanged();
                    break;
                case "CableTV":
                    tvChanged();
                    break;
                case "Phone":
                    phoneChanged();
                    break;
            }
        }

        $scope.loadNewPage = function (page) {
            AdvertResource.getAdverts(page,
                $scope.topsearch,
                $scope.testVal,
                $scope.rentVal,
                $scope.saleVal,
                $scope.searchCityValue,
                $scope.searchStreetValue,
                $scope.searchPostailValue,
                $scope.searchRegionValue,
                $scope.slider.min,
                $scope.slider.max,
                $scope.slider2.min,
                $scope.slider2.max,
                $scope.test["AC"],
                $scope.test["Fridge"],
                $scope.test["Internet"],
                $scope.test["CableTV"],
                $scope.test["Phone"]).then(function (items) {
                angular.forEach(items.content, function(value, key) {
                    PictureResource.getAdvertMainPicture(value.realestate.id).then(function (item) {
                        value.main_pic = item.pictureName;
                        $scope.adverts = items.content;
                    })
                });
                var pageList = [];
                for (i = 0; i < items.totalPages; i++) {
                    pageList[i] = i;
                }
                $scope.page = items;
                $scope.pageList = pageList;

            })
        }

        $scope.openAdvertPage = function (advert) {
            $scope.advert = advert;
        }

        getSearchItems(0);

        $scope.searchValue = null;
        $scope.searchCityValue = null;
        $scope.searchStreetValue = null;
        $scope.searchPostailValue = null;
        $scope.searchRegionValue = null;

        $scope.submitSearchForm = function(){
            AdvertResource.getAdverts(0,
                $scope.topsearch,
                $scope.testVal,
                $scope.rentVal,
                $scope.saleVal,
                $scope.searchCityValue,
                $scope.searchStreetValue,
                $scope.searchPostailValue,
                $scope.searchRegionValue,
                $scope.slider.min,
                $scope.slider.max,
                $scope.slider2.min,
                $scope.slider2.max,
                $scope.test["AC"],
                $scope.test["Fridge"],
                $scope.test["Internet"],
                $scope.test["CableTV"],
                $scope.test["Phone"]).then(function (items) {
                if($scope.topsearch){
                    $scope.searchValue = $scope.topsearch;
                }
                angular.forEach(items.content, function(value, key) {
                    PictureResource.getAdvertMainPicture(value.id).then(function (item) {
                        value.main_pic = item.pictureName;
                    })
                });
                var pageList = [];
                for (i = 0; i < items.totalPages; i++) {
                    pageList[i] = i;
                }
                console.log(items.totalPages);
                $scope.page = items;
                $scope.pageList = pageList;
                $scope.adverts = items.content;
            })
        }

        function airconditionChanged() {
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            getSearchItems(0);
        }

        function fridgeChanged() {
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            getSearchItems(0);
        }

        function internetChanged() {
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            getSearchItems(0);
        }

        function tvChanged() {
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            getSearchItems(0);
        }

        function phoneChanged() {
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            getSearchItems(0);
        }

        $scope.heatingChanged = function () {
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            getSearchItems(0);
        }
        
        $scope.rentChanged = function () {
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            getSearchItems(0);
        }

        $scope.saleChanged = function () {
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            getSearchItems(0);
        }

        $scope.setCity = function () {
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            if($scope.cityVal) {
                $scope.hideCityField = true;
            }
                $scope.searchCityValue = $scope.cityVal;
                getSearchItems(0);



        }

        $scope.removeCity = function () {
            $scope.hideCityField = false;
            $scope.cityVal = undefined;
            $scope.searchCityValue = undefined;
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            getSearchItems(0);

        }

        $scope.setStreet = function () {

            if($scope.streetVal) {
                $scope.hideStreetField = true;

                $scope.searchStreetValue = $scope.streetVal;
                getSearchItems(0);
            }
        }

        $scope.removeStreet = function () {
            $scope.hideStreetField = false;
            $scope.streetVal = undefined;
            $scope.searchStreetValue = undefined;
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            getSearchItems(0);

        }

        $scope.setPostal = function () {
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            if($scope.postailVal) {
                $scope.hidePostalField = true;
            }
            $scope.searchPostailValue = $scope.postailVal;
            getSearchItems(0);
        }

        $scope.removePostal = function () {
            $scope.hidePostalField = false;
            $scope.postailVal = undefined;
            $scope.searchPostailValue = undefined;
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            getSearchItems(0);
        }

        $scope.setRegion = function () {
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            if($scope.regionVal) {
                $scope.hideRegionField = true;
            }
            $scope.searchRegionValue = $scope.regionVal;
            getSearchItems(0);
        }

        $scope.removeRegion = function () {
            $scope.hideRegionField = false;
            $scope.regionVal = undefined;
            $scope.searchRegionValue = undefined;
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            getSearchItems(0);
        }

        $scope.searchByPrice = function () {
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            getSearchItems(0);
        }

        $scope.searchByArea = function () {
            if(!$scope.searchValue){
                $scope.topsearch = undefined;
            }
            getSearchItems(0);
        }

        
        function getSearchItems(page) {

            AdvertResource.getAdverts(page,
                $scope.topsearch,
                $scope.testVal,
                $scope.rentVal,
                $scope.saleVal ,
                $scope.searchCityValue,
                $scope.searchStreetValue,
                $scope.searchPostailValue,
                $scope.searchRegionValue,
                $scope.slider.min,
                $scope.slider.max,
                $scope.slider2.min,
                $scope.slider2.max,
                $scope.test["AC"],
                $scope.test["Fridge"],
                $scope.test["Internet"],
                $scope.test["CableTV"],
                $scope.test["Phone"]).then(function (items) {
                angular.forEach(items.content, function(value, key) {
                    PictureResource.getAdvertMainPicture(value.realestate.id).then(function (item) {
                        value.main_pic = item.pictureName;
                    })
                });
                var pageList = [];
                for (i = 0; i < items.totalPages; i++) {
                    pageList[i] = i;
                }
                $scope.page = items;
                $scope.pageList = pageList;
                $scope.adverts = items.content;
            })
        }

    })
})(angular);