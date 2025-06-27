function removeForm(button) {
  const row = button.closest("tr");
  if (confirm("Are you sure you want to delete this item?")) {
    row.remove();
  }
}