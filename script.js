var trials_per_thread = document.getElementById("tpt");
var threads = document.getElementById("t");
var chars = document.getElementById("chars");

var getJson = () => {
  return fetch("./src/results.json").then((res) => res.json());
};

getJson().then((res) => {
  let xVals = [];
  for (i = 1; i <= res.tests.length; i++) {
    xVals.push(i);
  }

  let vals = [];

  threads.innerHTML = `From 1 to ${res.threads_up_to} threads`;
  chars.innerHTML = `Chars to sort ${res.characters}`;
  res.tests.map((elem) => {
    vals.push(elem.time);
  });

  chart.data.labels = xVals;
  chart.data.datasets[0].data = vals;
  chart.options.scales.yAxes[0].ticks.max = Math.ceil(Math.max(...vals));
  chart.update();
});

let chart = new Chart("myChart", {
  type: "line",
  data: {
    labels: [],
    datasets: [
      {
        fill: false,
        lineTension: 0,
        backgroundColor: "red",
        borderColor: "red",
        data: [],
        label: "Nanoseconds",
      },
    ],
  },
  options: {
    legend: { display: true },
    scales: {
      yAxes: [{ ticks: { min: 0, max: 1 } }],
    },
  },
});

console.log(chart);
