var app = angular.module('books', ['ui.bootstrap']);

app.controller('BookListController', function ($scope, $rootScope, $http) {
	 	
	$scope.refreshTable = function () {
		$http.get('resources/books').success(function(data) {
			$scope.books = data;
		});    
	};
	
	$scope.$on('refreshTable', function () {
        $scope.refreshTable();
    });
	
	$scope.removeBook = function (book) {
		$http.delete('resources/books/'+book.id)
		.success(function (data) {
			$rootScope.$broadcast('bookDeleted');
			$scope.refreshTable();
		})
		.error(function (data) {
			$rootScope.$broadcast('error');
		});
    };
	
});

app.controller('BookController', function ($scope, $rootScope, $http) {
	

		$http.get('resources/authors').success(function(data) {
			$scope.authors = data;
		});    
	
	
	$scope.$on('loadData', function () {
		$http.get('resources/authors').success(function(data) {
			$scope.authors = data;
		}); 
	});
	 	
	$scope.addBook = function () {
		var par = {
			title: $scope.newTitle,
			price: $scope.newPrice,
			author: $scope.selected
		};
			
		var data = JSON.stringify(par);

		var config = {
			headers : {
				'Content-Type': 'application/json'
			}
		}

		$http.post('resources/books', data, config)
		.success(function (data, status, headers, config) {
			$rootScope.$broadcast('refreshTable');
			$rootScope.$broadcast('bookSaved');
			$scope.clearForm();
		})
		.error(function (data, status, header, config) {
			$rootScope.$broadcast('error');
		});
    };
	
	$scope.clearForm = function () {
        $scope.newTitle = null;
		$scope.newPrice = null;
		$scope.selected = '';
    };
});

app.controller('AuthorController', function ($scope, $rootScope, $http) {
	$scope.addAuthor = function () {
		var par = {
			name: $scope.newName
		};
			
		var data = JSON.stringify(par);

		var config = {
			headers : {
				'Content-Type': 'application/json'
			}
		}

		$http.post('resources/authors', data, config)
		.success(function (data, status, headers, config) {
			$rootScope.$broadcast('loadData');
			$rootScope.$broadcast('authorSaved');
			$scope.clearForm();
		})
		.error(function (data, status, header, config) {
			$rootScope.$broadcast('error');
		});
    };
	
	$scope.clearForm = function () {
        $scope.newName = null;
    };
});

app.controller('alertMessagesController', function ($scope) {

    $scope.$on('authorSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Author saved successfully!' }
        ];
    });

	$scope.$on('bookSaved', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Book saved successfully!' }
        ];
    });
	
    $scope.$on('bookDeleted', function () {
        $scope.alerts = [
            { type: 'success', msg: 'Book deleted successfully!' }
        ];
    });

    $scope.$on('error', function () {
        $scope.alerts = [
            { type: 'danger', msg: 'There was a problem in the server!' }
        ];
    });

    $scope.closeAlert = function (index) {
        $scope.alerts.splice(index, 1);
    };
});

