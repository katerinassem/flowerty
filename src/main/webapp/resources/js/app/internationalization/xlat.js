'use strict';
var xlat = angular.module('xlat', []);

xlat.factory('xlatService', [ '$interpolate', function($interpolate) {
    var currentLanguage = 'en';
    var tables = $.extend(true, {}, initialXlatTables);
    return {
	setCurrentLanguage : function(newCurrentLanguage) {
	    currentLanguage = newCurrentLanguage;
	},
	getCurrentLanguage : function() {
	    return currentLanguage;
	},
	xlat : function(label, parameters) {
	    var table = tables[currentLanguage];
	    while (table.hasOwnProperty(label) && $.isFunction(table[label])) {
		label = table[label](parameters);
	    }
	    if (table.hasOwnProperty(label)) {
		if (parameters == null || $.isEmptyObject(parameters)) {
		    return table[label];
		} else {
		    return $interpolate(table[label])(parameters);
		}
	    } else {
		return label;
	    }
	}
    };
} ]);

xlat.filter('xlat', [ 'xlatService', function(xlatService) {
    return function(label, parameters) {
	return xlatService.xlat(label, parameters);
    };
} ]);
