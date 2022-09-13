const loginBtn = document.querySelector(".submitBtn");

function loginFn () {
    if ( 2 > 5) {
        console.log("clicked");
    } else {
        console.log("false");
    }
}

loginBtn.addEventListener("click", loginFn);

