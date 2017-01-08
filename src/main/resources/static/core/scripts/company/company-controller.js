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
    
    app.controller('mycompany', function ($rootScope, $location, $scope, _, $log, $state, $uibModal, UsersResource, CompanyResource) {
        UsersResource.findUserCompany($scope.user.uid).then(function (item) {
            $scope.company = item;
            CompanyResource.getUsersForCompany(item.id).then(function (items) {
                console.log(items);
                $scope.users = items;
            });
        });

        $scope.openASModal = function (company) {
            var modalInstance = $uibModal.open({
                templateUrl: 'core/views/modals/newStaffMemberModal.html',
                controller: 'newStaffController',
                scope: $scope,
                resolve: {
                    company: function() {
                        return company;
                    }
                }
            });

            modalInstance.result.then(function(value) {
                $log.info('Modal finished its job at: ' + new Date() + ' with value: ' + value);
            }, function(value) {
                $log.info('Modal dismissed at: ' + new Date() + ' with value: ' + value);
            });
        }
    })

    app.controller('newStaffController', function($scope, $log, UsersResource, $uibModalInstance, CompanyResource) {
            var userId;
            $scope.addNewStaff = function(){
                var keywords = $scope.email;
                if(keywords) {
                    UsersResource.findKeywordsUser(keywords).then(function (item) {
                        if (item && $scope.user.uid != item.id) {
                            CompanyResource.isUserInCompany(item.id, 15).then(function (item2) {
                                if(item2){
                                    $scope.newStaffForm.email.$setValidity("forbiddenUser", false);
                                }else{
                                    userId = item.id;
                                    $scope.newStaffForm.email.$setValidity("forbiddenUser", true);
                                }
                            });
                            $scope.newStaffForm.email.$setValidity("forbiddenUser", false);
                        } else {
                            $scope.newStaffForm.email.$setValidity("forbiddenUser", false);
                        }
                    });
                }
            };

            $scope.addNewStaffOk = function(cid) {
                CompanyResource.addStaff(cid, userId).then(function (item) {
                    $scope.users.push(item);
                    $uibModalInstance.dismiss('cancel');
                });
            };

            $scope.cancel = function() {
                $uibModalInstance.dismiss('cancel');
            };
        });
})(angular);