var currentRow = null;

  function editRow(button) {

    currentRow = button.closest("tr");

    document.getElementById("item").value = currentRow.querySelector(".item").textContent;
    document.getElementById("qty").value = currentRow.querySelector(".qty").textContent;
    const categoryDropdown = document.getElementById("category");
    const otherCategoryInput = document.getElementById("othercategory");
    const customCategoryInput = document.getElementById("customCategoryInput");

    categoryDropdown.addEventListener("change", function () {
      if (this.value === "Others") {
        otherCategoryInput.style.display = "block";
      } else {
        otherCategoryInput.style.display = "none";
        customCategoryInput.value = "";
      }
    });
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

      const categoryDropdown = document.getElementById("addcategory");
      const otherCategoryBox = document.getElementById("newcategory");
      const customCategoryInput = document.getElementById("newcustomCategoryInput");

      categoryDropdown.addEventListener("change", function () {
        if (this.value === "Others") {
          otherCategoryBox.style.display = "block";
          customCategoryInput.focus();
        } else {
          otherCategoryBox.style.display = "none";
          customCategoryInput.value = "";
        }
      });
  

  }