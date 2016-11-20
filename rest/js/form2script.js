var gUserId = 0
var gPrefficsInfo = {}
var gFactorysList = []
var gProductsList = []
var gUserList = []

function sendAuthData() {
	requestUserId(document.getElementById("login").value, document.getElementById("password").value)
}	

function requestUserId(vLogin, vPassword){
	var vUserId = 0
	$.post("http://192.168.43.29:8080/auth/getUserId", { login: vLogin, password: vPassword },
	function(data, status){
		if(status == 'success') vUserId = data
		document.getElementById('authWindow').style.display = "none"
		gUserId = vUserId
		getPreffix(gUserId)
	});
}

function getPreffix(UserId){
	console.log(UserId)
	$.post("http://192.168.43.29:8080/get/preffixInfo", { userId: UserId },
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status);
		gPrefficsInfo = data
		if(typeof gPrefficsInfo.company === 'undefined')
			showFormsForUsers()
		else getFactoryList(gPrefficsInfo.company['id'])
	});
}

function getFactoryList(CompanyId){
	$.post("http://192.168.43.29:8080/get/factoryList", { companyId: CompanyId },
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status);
		gFactorysList = data
		getProductsList(gPrefficsInfo.company['id'])
	});
}

function getProductsList(CompanyId){
	$.post("http://192.168.43.29:8080/get/productList", { companyId: CompanyId },
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status);
		gProductsList = data
		getUserList()
	});
}

function getUserList(){
	$.post("http://192.168.43.29:8080/get/userList", {},
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status);
		gUserList = data
		showFormsForUsers()
	});
}
/*
function sendCompanyData() {
	var vCompanyName = document.getElementById('companyName').value
	var vCompanyAddress = document.getElementById('companyAddress').value
	$.post("http://192.168.43.29:8080/set/newCompany", { name: vCompanyName, address: vCompanyAddress},
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status)
	});
}
*/
function sendFactoryData() {
	var vCompanyId = gPrefficsInfo.company.id
	var vFactoryAddress = document.getElementById('factoryAddress').value
	$.post("http://192.168.43.29:8080/set/newFactory", { companyId: vCompanyId, address: vFactoryAddress},
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status)
	});
}

function sendPreffixData() {
	var vPreffixName = document.getElementById('preffixName').value.split('.')
	var vCompanyName = document.getElementById('companyName').value
	var vCompanyAddress = document.getElementById('companyAddress').value
	var vUserId = document.getElementById('dUserList').options[document.getElementById('dUserList').selectedIndex].getAttribute('idValue')
	$.post("http://192.168.43.29:8080/set/newPreffix", { registry: vPreffixName[0], registrant: vPreffixName[1], name: vCompanyName, address: vCompanyAddress, userId: vUserId},
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status)
	});
}

function sendProductData() {
	var vCompanyId = gPrefficsInfo.company.id
	var vProductName = document.getElementById('productName').value
	var vShelfTime = document.getElementById('shelfTime').value
	var vAdditionalInfo = document.getElementById('prAdditionalInfo').value
	$.post("http://192.168.43.29:8080/set/newProduct", { companyId: vCompanyId, productName: vProductName, shelfTime: vShelfTime, info: vAdditionalInfo},
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status)
	});
}

function createUserList(){
	var name = 'UserList'
	var successAW = document.getElementById('preffixAddWindow')
		
	var selectDes= document.createElement('label')
	selectDes.setAttribute('for', 'd' + name)
	selectDes.setAttribute('id', 'l' + name)
	selectDes.innerHTML = 'Список пользователей: '
		
	var selectNew = document.createElement('select')
	selectNew.setAttribute('id', 'd' + name)
		
	for(var ix in gUserList){
		var selectElement = document.createElement('option')
		selectElement.setAttribute('id', 'userList' + gUserList[ix].id)
		selectElement.setAttribute('value', gUserList[ix].login)
		selectElement.setAttribute('valuePasswrd', gUserList[ix].password)
		selectElement.setAttribute('idValue', gUserList[ix].id)
		selectElement.innerHTML = gUserList[ix].login
		selectNew.appendChild(selectElement)
	}
	
	var selectButton= document.createElement('button')
	selectButton.setAttribute('id', 'sendPreffixData')
	selectButton.setAttribute('onclick', 'sendPreffixData()')
	selectButton.innerHTML = 'Отправить'
	
	successAW.appendChild(selectDes)
	successAW.appendChild(selectNew)
	successAW.appendChild(selectButton)
}

function showFormsForUsers(){
	//document.getElementById("companyAddWindow").style.display = 'inline'
	document.getElementById("factoryAddWindow").style.display = 'inline'
	document.getElementById("preffixAddWindow").style.display = 'inline'
	createUserList()
	document.getElementById("productAddWindow").style.display = 'inline'
}
