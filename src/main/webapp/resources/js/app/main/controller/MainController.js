'use strict';
/**
 * Created by Катерина on 20.04.2015.
 */
angular.module('flowertyApplication')

angular.module('flowertyApplication').controller('MainController',
    function ($scope, $http, $filter, $window, AuthServerProvider, $localStorage, notificationService, xlatService, dynamicSearchFactory) {

        AuthServerProvider.setLoggedUser($scope);

        $scope.current = {
            isLogged: false,
            user: {},
            errorLogin: false
        };

        $scope.current.resetBasket = function () {
            $scope.current.basket.info.itemsCount = 0;
            $scope.current.basket.info.fullCost = 0;
            $scope.current.basket.items = {};
            delete $localStorage.cart;
        };

        $scope.current.basket = {
            info: {
                itemsCount: 0,
                fullCost: 0
            },
            items: {}
        };

        $scope.current.logOut = function () {
            AuthServerProvider.logout($scope, $location);
            $scope.current.resetBasket();
        };

        $scope.pagination = {
            getPagesCount: function () {
            },
            pageClass: function (page) {
            },
            getPage: function (page) {
            },
            getPreviousPage: function () {
            },
            getNextPage: function () {
            },
            changeLimit: function (limit) {
            },
            limit: 10,
            limits: [],
            canChangeLimit : true
        };

        $scope.dynamicSearch = dynamicSearchFactory.dynamicSearch;

        $scope.setCurrentLanguage = function(language) {
            $localStorage.language = language;
            xlatService.setCurrentLanguage(language);
            $window.location.reload();
        };

        $scope.notification = notificationService.getNotificationBundle();

        $scope.localization = {
            language : 'en'
        };

        if ($localStorage.language) {
            $scope.localization.language = $localStorage.language;
            xlatService.setCurrentLanguage($localStorage.language);
        }
    });