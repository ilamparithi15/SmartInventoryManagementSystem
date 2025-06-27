function addForm(event){

    event.preventDefault(); 

    const item = document.getElementById("additem").value;
    const qty = document.getElementById("addqty").value;
    const location = document.getElementById("addlocation").value;
    const otherCategoryInput = document.getElementById("newcategory");
    const customCategoryValue = document.getElementById("newcustomCategoryInput").value.trim();

    let category = document.getElementById("addcategory").value.trim();
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
        const tableBody = document.getElementById("inventoryTable");

        const newRow = document.createElement("tr");

        newRow.innerHTML = `
        <td class="item">${item}</td>
        <td class="qty">${qty}</td>
        <td class="category">${category}</td>
        <td class="location">${location}</td>
        <td>
            <button onclick="editRow(this)" class="btn btn-sm btn-outline-primary">Edit</button>
            <button type="submit" class="btn btn-sm btn-outline-danger" onclick="removeForm(this)">Delete</button>
        </td>
        `;

        tableBody.appendChild(newRow); 

        newRow.setAttribute("data-category", category);


        document.getElementById("addItemForm").reset();

        confirm(item+" has added");

        document.getElementById("add").style.display = "none";
        document.getElementById("main-part1").style.display = "block";
        document.getElementById("main-part2").style.display = "block";
  
    }
    
  }
