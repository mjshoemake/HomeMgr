
function setCheckboxes() {
	var form = document.forms[0];
	var val = form.selectAll.checked;
	for (var i=0; i < form.elements.length; i++) {
		if (form.elements[i].type == "checkbox") {
			form.elements[i].checked = val;
		}  
	}
}

function confirmDelete(type, target) {
	if (window.confirm("Are you sure you want to delete the selected " + type + "(s)?")) {
		//var form = document.forms[0];
	 	//form.submit();
		window.location="file://localhost/Users/mshoemake/IdeaProjects/HomeMgr/prototype/" + target; 	 	
	}
}

function confirmDeleteAll() {
	if (window.confirm("WARNING: This will delete all users in the system. Are you sure you want to cotinue?")) {
		window.location="file://localhost/Users/mshoemake/IdeaProjects/HomeMgr/prototype/" + target; 
	}
}

function getCheckedBoxes(chkboxName) {
	var checkboxes = document.getElementsByName(chkboxName);
	var checkboxesChecked = [];
	// loop over them all
	for (var i=0; i<checkboxes.length; i++) {
		// And stick the checked ones onto an array...
		if (checkboxes[i].checked) {
			checkboxesChecked.push(checkboxes[i]);
		}
	}
	// Return the array if it is non-empty, or null
	return checkboxesChecked.length > 0 ? checkboxesChecked : null;
}

function setAllCheckBoxes(chkbox, chkboxName) {
	var checkboxes = document.getElementsByName(chkboxName);
	// loop over them all
	for (var i=0; i<checkboxes.length; i++) {
		// And stick the checked ones onto an array...
		checkboxes[i].checked = chkbox.checked
	}
	// Return the array if it is non-empty, or null
	return checkboxesChecked.length > 0 ? checkboxesChecked : null;
}

/*
function goto(target) {
	window.location="file://localhost/Users/mshoemake/IdeaProjects/HomeMgr/prototype/" + target; 
}
*/
