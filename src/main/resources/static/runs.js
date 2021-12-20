
function createRun(form){
    var date = form.date.value;
    var distance = form.distance.value;
    var duration = form.duration.value;
    var comments = form.comments.value;


    var url = "http://localhost:8080/addRunToRunner?comments=" + comments  + "&distance=" + distance  + "&duration=" + duration + "&runDate=" + date + "&username=" + localStorage.getItem('username')


    let ajax = new XMLHttpRequest();

    ajax.open("POST", url);
    ajax.setRequestHeader('Content-Type', 'application/json');
    ajax.send();

    ajax.onload = () => {
    if (ajax.status == 200) {
           window.location.reload();
    } else {
        alert("Error logging run");
    }
    }
}

function populateTable(){

   var table = document.getElementById("runTable");

   var url = "http://localhost:8080/getRunner?username=" + localStorage.getItem('username')
   let ajax = new XMLHttpRequest();

   ajax.open("GET", url, true);

   ajax.send();

   ajax.onload = () => {
   var runner = JSON.parse(ajax.response);
   var runs = runner.runs;

    runs.sort(function(a,b){
      return new Date(b.runDate) - new Date(a.runDate);
    });

    runs.forEach( run => {
      let row = table.insertRow();
       var cell1 = row.insertCell(0);
       var cell2 = row.insertCell(1);
       var cell3 = row.insertCell(2);
       var cell4 = row.insertCell(3);
       var cell5 = row.insertCell(4);
       var cell6 = row.insertCell(5);

          var date = run.runDate;
          var distance = run.distance;
          var duration = run.time;
          var avgSpeed = run.avgSpeed;
          var comments = run.comments;
             cell1.innerHTML = date;
             cell2.innerHTML = distance;
             cell3.innerHTML = duration;
             cell4.innerHTML = avgSpeed;
             cell5.innerHTML = comments;
             cell6.innerHTML = "";
    });

        var rows = document.getElementById("runTable").rows;
        for (i = 1; i < rows.length; i++) {
            rows[i].onclick = function(){ return function(){
                   var id = this.cells[0].innerHTML;
                   var ind = this.rowIndex - 2
                   populateRunDisplay(runs[ind])
            };}(rows[i]);
        }
   }
}

function populateRunDisplay(run){
    localStorage.setItem('runId', run.runId)
    document.getElementById("eDate").value = run.runDate
    document.getElementById("eDistance").value = run.distance
    document.getElementById("eDuration").value = run.time
    document.getElementById("eComments").value = run.comments
    $('#displayModal').modal('show');
}

function editRun(form){
    var date = form.eDate.value;
    var distance = form.eDistance.value;
    var duration = form.eDuration.value;
    var comments = form.eComments.value;


    var url = "http://localhost:8080/editRunnerRun?username=" + localStorage.getItem('username') +"&runId=" + localStorage.getItem('runId') + "&distance=" + distance  + "&duration=" + duration + "&runDate=" + date + "&comments=" + comments


    let ajax = new XMLHttpRequest();

    ajax.open("POST", url);
    ajax.setRequestHeader('Content-Type', 'application/json');
    ajax.send();

    ajax.onload = () => {
    if (ajax.status == 200) {
           window.location.reload();
    } else {
        alert("Error editing run");
    }
    }
}

function deleteRun(form){

    var url = "http://localhost:8080/deleteRunFromRunnerByUsername?username=" + localStorage.getItem('username') +"&runId=" + localStorage.getItem('runId')


    let ajax = new XMLHttpRequest();

    ajax.open("DELETE", url);
    ajax.setRequestHeader('Content-Type', 'application/json');
    ajax.send();

    ajax.onload = () => {
    if (ajax.status == 200) {
           window.location.reload();
    } else {
        alert("Error deleting run");
    }
    }
}