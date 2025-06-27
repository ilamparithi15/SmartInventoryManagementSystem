var currentRow = null;

function updateForm(event) {
  event.preventDefault();

  if (!currentRow) {
    alert("No row selected to update.");
    return;
  }


  const item = document.getElementById("item").value.trim();
  const qty = document.getElementById("qty").value.trim();
  const location = document.getElementById("location").value.trim();
  let category = document.getElementById("category").value.trim();
  const customCategoryValue = document.getElementById("customCategoryInput").value.trim();

  if (category === "Others") {
    if (customCategoryValue === "") {
      alert("Please enter a new category.");
      return;
    }
    category = customCategoryValue;


    const filterDropdown = document.getElementById("categoryFilter");
    let exists = Array.from(filterDropdown.options).some(opt => opt.value === category);
    if (!exists) {
      const newOption = document.createElement("option");
      newOption.value = category;
      newOption.textContent = category;
      filterDropdown.appendChild(newOption);
    }
  }

  if (item === "" || qty === "" || category === "" || location === "") {
        alert("Fill all the fields to add");
        return; 
    }

  if(qty<=0){
    alert("The quantity should not be empty and no -ve stocking🎀");
    return; 
  }

  else if(qty<11){
        alert("The Quantity is Low, must be more than 10");
        return;
    }

  else{
    currentRow.querySelector(".item").textContent = item;
    currentRow.querySelector(".qty").textContent = qty;
    currentRow.querySelector(".category").textContent = category;
    currentRow.querySelector(".location").textContent = location;

    currentRow.setAttribute("data-category", category);

    document.getElementById("editform").style.display = "none";
    document.getElementById("main-part1").style.display = "block";
    document.getElementById("main-part2").style.display = "block";
  }

  
}
