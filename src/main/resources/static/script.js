

    function check(form){

    var username = form.username.value
    var password = form.password.value

    localStorage.setItem('username', username)

    var ajax = new XMLHttpRequest();
    ajax.onload = functionName;
    var url = "http://localhost:8080/login?password=" + password + "&username=" + username

    ajax.open("GET", url , true);
    ajax.send();
    function functionName() {

    if (ajax.status == 200) {
         window.open('home.html', '_self')
    } else {
         alert("Incorrect password or username")
    }
    }


 }

  function register(form){

 	var url = "http://localhost:8080/addRunner?email=" + form.email.value + "&first=" + form.firstName.value + "&last=" + form.lastName.value + "&password=" + String(form.password.value) + "&username=" + form.username.value;

    let ajax = new XMLHttpRequest();

    ajax.open("POST", url);
    ajax.setRequestHeader('Content-Type', 'application/json');
    ajax.send();

    ajax.onload = () => {
    if (ajax.status == 201) {

        window.open('index.html', '_self')

    } else {
        alert("Error creating account")
    }
    }

  }

function getProfileInfo(){

   var url = "http://localhost:8080/getRunner?username=" + localStorage.getItem('username')
   let ajax = new XMLHttpRequest();

   ajax.open("GET", url, true);

   ajax.send();

   ajax.onload = () => {
   var runner = JSON.parse(ajax.response)
   var firstName = runner.firstName;
   var lastName = runner.lastName;
   var email = runner.email;
   var province = runner.province;
   var city = runner.city;

   document.getElementById("firstName").innerHTML = firstName;
   document.getElementById("lastName").innerHTML = lastName;
   document.getElementById("userEmail").innerHTML = email;
   document.getElementById("province").innerHTML = province;
   document.getElementById("city").innerHTML = city;

   getEditProfileInfo(firstName, lastName, email, province, city);

   }
}

function getEditProfileInfo(firstName, lastName, email, province, city){
        document.getElementById("eFirstName").value = firstName;
        document.getElementById("eLastName").value = lastName;
        document.getElementById("eEmail").value = email;

        document.getElementById("profileHeader").innerHTML = firstName + "'s Profile";
        document.getElementById("nameHeader").innerHTML = firstName + " " + lastName;

        document.getElementById("eProvince").value = province;
        document.getElementById("eCity").value = city;
}

function setProfileInfo(form){

     var firstName = form.eFirstName.value
     var lastName = form.eLastName.value
     var email = form.eEmail.value
     var province = form.eProvince.value
     var city = form.eCity.value

     var url = "http://localhost:8080/editRunner?username=" + localStorage.getItem('username') + "&firstName=" +firstName + "&lastName=" +lastName + "&email=" +email + "&province=" + province + "&city=" + city

    let ajax = new XMLHttpRequest();
    ajax.open("POST", url);
    ajax.setRequestHeader('Content-Type', 'application/json');
    ajax.send();

    ajax.onload = () => {
    if (ajax.status == 200) {
       window.location.reload();
    } else {
        alert("Error saving info")
    }
    }

}


function deleteAccount(){
     var url = "http://localhost:8080/deleteRunner?username=" + localStorage.getItem('username')

      let ajax = new XMLHttpRequest();
         ajax.open("DELETE", url);
         ajax.setRequestHeader('Content-Type', 'application/json');
         ajax.send();

         ajax.onload = () => {
         if (ajax.status == 204) {
            window.open('index.html', '_self')
         } else {
             alert("Error deleting account")
         }
         }
}


