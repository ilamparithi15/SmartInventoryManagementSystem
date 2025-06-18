function addForm(event){

    

    event.preventDefault(); 

    const item = document.getElementById("additem").value;
    const qty = document.getElementById("addqty").value;
    const category = document.getElementById("addcategory").value;
    const location = document.getElementById("addlocation").value;


    if (item === "" || qty === "" || category === "" || location === "") {
        alert("Fill all the fields to add");
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

        document.getElementById("addItemForm").reset();

        confirm(item+" has added");

        document.getElementById("add").style.display = "none";
        document.getElementById("main-part1").style.display = "block";
        document.getElementById("main-part2").style.display = "block";

       

        
    }
    
  }