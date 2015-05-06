/**
 * @author Eugene Putsykovich(slesh) Apr 7, 2015
 *
 * Contains filters, directions and other for util stuff
 */
angular.module("flowertyApplication.utilModule", [])

    /*
     * show notification message
     */
    .directive('notification', ['$timeout', function ($timeout) {
        return {
            restrict: 'E',
            template:"<div class='alert alert-{{alertData.type}}' ng-show='alertData.message' role='alert' data-notification='{{alertData.status}}'>{{alertData.message}}</div>",
            scope:{
                alertData:"="
            },
            replace:true,
            link: function(scope, elem, attrs) {
                attrs.$observe('notification', function (value) {
                    if (value === 'show') {
                        $timeout(function () {
                            scope.alertData.status = 'hide';
                            scope.alertData.message = '';
                        }, 3000);
                    }
                });
            }
        };
    }])


    /*
     * create array of numbers as objects to iterate with 'in' operator
     */
    .filter('flowerRange', function() {
        return function(range, total) {
            if (range === undefined || total === undefined) {
                return '';
            }
            total = parseInt(total);
            for (var i = 0; i < total; ++i) {
                range.push(i);
            }

            return range;
        };
    })

    /*
     * make separating, paste together tokens.
     * example: UPPER_CASE -> Upper case. I this case, seprator='_', connector: ' '
     */
    .filter( "flowerSplit", function() {
        return function(value, separator, connector){
            if (value === undefined) {
                return '';
            }
            var tokens = value.split(separator);
            var result = '';
            var l = tokens.length;
            for(var i = 0; i < l; ++i){
                result += tokens[i] + (i != l - 1 ? connector : '');
            }

            return result;
        }
    })
    /*
     * filter to change case all character 'from' by 'length'.
     *
     */
    .filter("flowerCase", function() {
        return function(value, from, length, isUp){
            if (value === undefined) {
                return '';
            }
            var left = value.substr(0, from);
            var center = value.substr(from, length);
            center = isUp ? center.toUpperCase() : center.toLowerCase()
            var right = value.substr(from + length, value.length);

            return left.concat(center).concat(right);
        }
    })

    .filter('bySurname', ['$http', function($http){
        return function(offeredContacts, model, filterCallback){
            enteredSurname = model.enteredSurname;
            if(enteredSurname.length < 3){
                return [];
            }

            console.log('Searching by surname: ' + enteredSurname);

            $http({
                url: 'contact/search/' + enteredSurname,
                method: 'GET'
            }).success(function(data, status, headers, config) {
                filterCallback(model,data);
            }).error(function(data, status, headers, config) {
                console.log("Exception details in bySurname filter : " + JSON.stringify({data: data}));
                return [];
            });
        }
    }]);

	
