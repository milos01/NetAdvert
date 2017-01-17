/**
 * Created by milosandric on 28/12/2016.
 */
(function(angular){
    app.controller('usercred', function ($rootScope, $location, $scope, _, $log, $state, UsersResource, CompanyResource, socket) {

        $scope.updateLname = $scope.user.lname;
        $scope.updateFname = $scope.user.fname;
        $scope.updateUserInfo = function(){
            $scope.user.lname = $scope.updateLname;
            $scope.user.fname = $scope.updateFname;
            UsersResource.updateUser($scope.user.email, $scope.user.fname, $scope.user.lname);
        }

        CompanyResource.isUserAvailable($scope.user.uid).then(function (item) {
            if(item){
                $scope.showSelect = false;
                $scope.assignText = "Assigned to " + item.company.company_name;
            }else{
                $scope.assignText = "Assign to company";
                $scope.showSelect = true;
            }
        });

        UsersResource.getUserAdverts($scope.user.uid).then(function (response) {
            $scope.userAdverts = response;
        });

        CompanyResource.getAllCompanys().then(function (items) {
            $scope.allCompanies = items;
        });


        $scope.getAllCompanies = function () {
            var cid = $scope.myComp.id;
            var userId = $scope.user.uid;
            CompanyResource.addStaff(cid, userId).then(function (item) {
                $scope.assignText = "Assigned to " + item.company.company_name;
                $scope.showSelect = false;
                socket.emit('notifyUserNewStaff', $scope.user);
            });
        }
    })
    
    app.controller('profile',function ($state) {
        $state.go('creds');
    })
})(angular);