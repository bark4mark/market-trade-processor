var TradeMessageModel = Backbone.Model.extend({
  url: '/api/trademessage/live'
});

var GadgetModel = Backbone.Model.extend({
  url: '/api/trademessage/currencystats'
});

var chart;

var context = {
  data : {},
  latest : undefined,

  tradeMessage: new TradeMessageModel(),
  gadgets: new GadgetModel(),

  pollOptions: {
    refresh: 1000,
    retryRequestOnFetchFail: true
  }
};

$( window ).resize(function() {
  redrawMap (context.data);
});

context.tradeMessage.on('refresh:loaded', function (){

  var origin = context.tradeMessage.get('originatingCountry'),
      amountSell = context.tradeMessage.get('amountSell'),
      tradeMessageId = context.tradeMessage.get('id');

  if (context.latest != undefined)
    if (context.latest === tradeMessageId)
      return;

  context.data = google.visualization.arrayToDataTable([
    ['Region', 'Amount'],
    [origin, amountSell]
  ]);

  redrawMap (context.data);
  context.latest = context.tradeMessage.get('id');
});

google.load("visualization", "1", {packages:["geochart"]});
google.setOnLoadCallback(drawRegionsMap);

function drawRegionsMap() {
  var data = google.visualization.arrayToDataTable([
    ['Region', 'Amount']
  ]);

  chart = new google.visualization.GeoChart(document.getElementById('world-map'));
  chart.draw(data, {});

  _.extend(TradeMessageModel.prototype, BackbonePolling);
  context.tradeMessage.configure(context.pollOptions);
  context.tradeMessage.startFetching();
  context.gadgets.fetch ().done(function (data){
    $('#mostBought').text (data.mostPurchased);
    $('#mostSold').text (data.mostSold);
  });
}

function redrawMap (data) {
  chart.draw (data, {legend: 'none'});
  $('#latestSell').text(context.tradeMessage.get('amountSell'));
  $('#latestCur').text(context.tradeMessage.get('currencyFrom'));
  $('#latestRate').text(context.tradeMessage.get('rate'));
  $('#latestLoc').text(context.tradeMessage.get('originatingCountry'));
  $('#log').show();
}
