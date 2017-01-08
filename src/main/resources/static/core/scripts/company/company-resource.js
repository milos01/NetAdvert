/**
 * Created by milosandric on 05/01/2017.
 */
(function(angular){
    app.factory('CompanyResource', function (Restangular, $log, _) {
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

        retVal.getUsersForCompany = function (cid) {
            return Restangular.one('company', cid).getList('allusers').then(function (items) {
                return items;
            });
        }

        retVal.isUserInCompany = function(uid, cid){
            return Restangular.one('company').one('isInCompany').get({uid: uid, cid: cid}).then(function (item) {
                return item;
            });
        }

        retVal.addStaff = function (cid, uid) {
            return Restangular.one('company', cid).one('user', uid).post().then(function (item) {
                return item;
            });
        }
        return retVal;
    })
})(angular);