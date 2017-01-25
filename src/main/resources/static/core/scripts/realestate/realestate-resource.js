(function(angular){
    app.factory('RealestateResource', function (Restangular, _) {
        var retVal = {};

        retVal.getCategories = function() {
            return Restangular.all("realestate/category").getList().then(function(entries) {
            	return entries;
            });
        };
        
        retVal.getTypes = function() {
            return Restangular.all("realestate/type").getList().then(function(entries) {
            	return entries;
            });
        };
        
        retVal.getCategoryEquipment = function(id) {
            return Restangular.one("realestate/category", id).getList("equipment").then(function(entries) {
            	return entries;
            });
        };

        retVal.getTechEquipment = function () {
            return Restangular.all('realestate/equipment').getList().then(function (items) {
                return items;
            });
        }
        
        

        return retVal;
    })
})(angular);