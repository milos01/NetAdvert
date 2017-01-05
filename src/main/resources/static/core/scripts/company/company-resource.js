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

        retVal.getAllCompanys = function () {
            return Restangular.one('company').all('all').get().then(function (items) {
                return items;
            });
        }
        
        retVal.checkIfUserMainOnCompany = function (id) {
            return Restangular.one('company').one('isMainuser').get({uid: id}).then(function (item) {
                return item;
            });
        }

        return retVal;
    })
})(angular);