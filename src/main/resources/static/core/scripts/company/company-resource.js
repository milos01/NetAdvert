/**
 * Created by milosandric on 05/01/2017.
 */
(function(angular){
    app.factory('CompanyResource', function (Restangular, _) {
        var retVal = {};

        retVal.addCompany = function(user, companyName) {

            return Restangular.all('company').post({company_name :companyName, user_email: user}).then(function (data) {
                return data;
            });

        };

        return retVal;
    })
})(angular);