cookbooks.factory('cookbookService', function() {
  var ListService = {};
  var cookbookList = [
    {'name': '30 Minute Meals For Dummies'}, 
    {'name': 'allrecipes.com'}, 
    {'name': 'Betty Crocker'}, 
    {'name': 'Chris Carmichael\'s Fitness Cookbook'}, 
    {'name': 'Cooking Light Magazine'}, 
    {'name': 'Gluten Free and Easy'}, 
    {'name': 'Gran'}, 
    {'name': 'Grandma Grace'}, 
    {'name': 'Grandma Hilda'}, 
    {'name': 'Kraft Shredded Cheese Packet'}, 
    {'name': 'Lazy Day Cookin'}, 
    {'name': 'Mamaw Shoemake'}, 
    {'name': 'Personal Cookbook'}, 
    {'name': 'Pillsbury Annual Recipes 2010'}, 
    {'name': 'The Gluten Free Bible'}, 
    {'name': 'The Great Potato Cookbook'}, 
    {'name': 'www.stephanieodea.com'}, 
    {'name': 'www.tasteofhome.com'} 
  ];
  cookbookService.getItem = function(index) { return cookbookList[index]; }
  cookbookService.addItem = function(item) { cookbookList.push(item); }
  cookbookService.removeItem = function(item) { cookbookList.splice(cookbookList.indexOf(item), 1) }
  cookbookService.size = function() { return cookbookList.length; }

  return cookbookService;
});

/*
function Ctrl1($scope, ListService) {
  //Can add/remove/get items from shared list
}

function Ctrl2($scope, ListService) {
  //Can add/remove/get items from shared list
}
*/