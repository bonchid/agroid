var gUserId = 0
var gPrefficsInfo = {}
var gFactorysList = []
var gProductsList = []

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
	$.post("http://192.168.43.29:8080/get/preffixInfo", { userId: UserId },
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status);
		gPrefficsInfo = data
		getFactoryList(gPrefficsInfo.company['id'])
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
	var vCompanyName = gPrefficsInfo.company.name
	var vCompanyAddress = gPrefficsInfo.company.address
	$.post("http://192.168.43.29:8080/set/newPreffix", { registry: vPreffixName[0], registrant: vPreffixName[1], name: vCompanyName, address: vCompanyAddress,userId: gUserId},
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

function showFormsForUsers(){
	//document.getElementById("companyAddWindow").style.display = 'inline'

	document.getElementById("factoryAddWindow").style.display = 'inline'
	
	document.getElementById("preffixAddWindow").style.display = 'inline'
	
	document.getElementById("productAddWindow").style.display = 'inline'

}
