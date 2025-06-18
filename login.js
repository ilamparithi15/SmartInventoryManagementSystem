function closeform(event) {

    event.preventDefault();

  const mailInput = document.getElementById("mail1");
  const passwordInput = document.getElementById("pass1");

  const mail = mailInput.value.trim();
  const password = passwordInput.value.trim();

  const index_at = mail.indexOf("@");
  const index_dot = mail.lastIndexOf(".");
  const length = mail.length;

  if ((mail === "" || mail === null) && (password === "" || password === null)) {
    alert("Enter a Mail ID and Password");
    return;
  }

  if (mail === "" || mail === null) {
    alert("Enter a Mail ID");
    mailInput.focus();
    return;
  }

  if (index_at <= 0 || index_dot <= index_at + 1 || index_dot >= length - 1) {
    alert("Enter a valid Mail ID");
    mailInput.focus();
    return;
  }

  if (password === "" || password === null) {
    alert("Enter a Password");
    passwordInput.focus();
    return;
  }

  if (
    password.length < 4 ||
    (password.indexOf("@") === -1 &&
     password.indexOf("$") === -1 &&
     password.indexOf("#") === -1 &&
     password.indexOf("&") === -1 &&
     password.indexOf("_") === -1)
  ) {
    alert("The password must be at least 4 characters and include one of the symbols (@, $, #, &, _)");
    return;
  }

  else{
    window.location.href = "dashboard.html";
  }
  
}
