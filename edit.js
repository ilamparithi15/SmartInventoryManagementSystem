var currentRow = null;

  function editRow(button) {

    currentRow = button.closest("tr");

    document.getElementById("item").value = currentRow.querySelector(".item").textContent;
    document.getElementById("qty").value = currentRow.querySelector(".qty").textContent;
    document.getElementById("category").value = currentRow.querySelector(".category").textContent;
    document.getElementById("location").value = currentRow.querySelector(".location").textContent;

    document.getElementById("main-part1").style.display = "none";
    document.getElementById("main-part2").style.display = "none";

    document.getElementById("editform").style.display = "block";
  }

  function Form(){
    document.getElementById("main-part1").style.display = "none";
    document.getElementById("main-part2").style.display = "none";

    document.getElementById("add").style.display = "block";
  }
