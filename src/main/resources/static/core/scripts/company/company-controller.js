/**
 * Created by milosandric on 04/01/2017.
 */
(function(angular){
    app.controller('company', function ($rootScope, $location, $scope, _, $log, $state, UsersResource, CompanyResource) {

        CompanyResource.getAllCompanys().then(function (items) {
            console.log(items);
            $scope.allCompanies = items;
        });

        $scope.searchUser = function () {
            var keywords = $scope.masterEmail;
            if(keywords) {
                UsersResource.findKeywordsUser(keywords).then(function (item) {
                    console.log(item);
                    if (item) {
                        $scope.addNewCompany.masterEmail.$setValidity("noEmail", true);
                    } else {

                        $scope.addNewCompany.masterEmail.$setValidity("noEmail", false);
                    }
                });
            }
        }

        $scope.addCompany = function () {
            var companyName = $scope.companyName;
            var user = $scope.masterEmail;
            CompanyResource.addCompany(user, companyName).then(function (item) {
                $scope.allCompanies.push(item);
            });
        }
    })
})(angular);