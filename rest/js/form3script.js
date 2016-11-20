var gUserId = 0
var gPrefficsInfo = {}
var gFactorysList = []
var gProductsList = []
var gUserList = []
var gProductInfo = {}

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

function getProductInfoGP(vSuffix){
	$.post("http://192.168.43.29:8080/get/productInfo", { registry: gPrefficsInfo.registry, registrant: gPrefficsInfo.registrant, suffix: vSuffix},
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status);
		//gProductInfo = 
		requestPathFromServer(data)
	});
}

function getProductInfoSD(vSuffix){
	$.post("http://192.168.43.29:8080/get/productInfo", { registry: gPrefficsInfo.registry, registrant: gPrefficsInfo.registrant, suffix: vSuffix},
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status);
		//gProductInfo = data
		sendDistributorInfoToServer(data)
	});
}

function sendPathReq(){
	getProductInfoGP(document.getElementById('gpSuffixName').value)
}

function createPathTable(pathData, ProductInfo){
	var id = []
	var doi = []
	var company = []
	var count = 1
	
	for(var ix in pathData){
		id.push(pathData[ix].id)
		doi.push(pathData[ix].suffix.preffix.registry + "." + pathData[ix].suffix.preffix.registrant + "/" + pathData[ix].suffix.suffix)
		company.push(pathData[ix].suffix.preffix.company.name)
		count++
	}
	
	var name = 'PathMap'
	
	var mainPath= document.createElement('div')
	mainPath.setAttribute('id', 'd' + name)
	
	var selectDes = document.createElement('label')
	selectDes.setAttribute('for', 't' + name)
	selectDes.setAttribute('id', 'l' + name)
	selectDes.innerHTML = 'Поставщики и производители, через которых прошел товар ' + ProductInfo.product.productName + ': '
	
	var mainTable= document.createElement('tabel')
	mainTable.setAttribute('id', 't' + name)
	
	var mainTableRowHElem= document.createElement('tr')
	mainTableRowHElem.setAttribute('id', 'htr' + name)
		
	var mainTableHElemId= document.createElement('td')
	mainTableHElemId.setAttribute('id', 'htd' + name + 'Id')
	mainTableHElemId.innerHTML = "ID поставщика/производителя в базе данных"
	mainTableRowHElem.appendChild(mainTableHElemId)

	var mainTableHElemDoi= document.createElement('td')
	mainTableHElemDoi.setAttribute('id', 'htd' + name + 'Doi')
	mainTableHElemDoi.innerHTML = "DOI поставщика/производителя"
	mainTableRowHElem.appendChild(mainTableHElemDoi)
		
	var mainTableHElemCompany= document.createElement('td')
	mainTableHElemCompany.setAttribute('id', 'htd' + name + 'Company')
	mainTableHElemCompany.innerHTML = "Наименование поставщика/производителя"
	mainTableRowHElem.appendChild(mainTableHElemCompany)
		
	mainTable.appendChild(mainTableRowHElem)
	
	for(var ix = 0; ix < count-1; ++ix){
		var mainTableRowElem= document.createElement('tr')
		mainTableRowElem.setAttribute('id', 'tr' + name + ix)
		
		var mainTableElemId= document.createElement('td')
		mainTableElemId.setAttribute('id', 'td' + name + 'Id' + ix)
		mainTableElemId.innerHTML = id[ix]
		mainTableRowElem.appendChild(mainTableElemId)

		var mainTableElemDoi= document.createElement('td')
		mainTableElemDoi.setAttribute('id', 'td' + name + 'Doi' + ix)
		mainTableElemDoi.innerHTML = doi[ix]
		mainTableRowElem.appendChild(mainTableElemDoi)
		
		var mainTableElemCompany= document.createElement('td')
		mainTableElemCompany.setAttribute('id', 'td' + name + 'Company' + ix)
		mainTableElemCompany.innerHTML = company[ix]
		mainTableRowElem.appendChild(mainTableElemCompany)
		
		mainTable.appendChild(mainTableRowElem)
	}	
		
	mainPath.appendChild(selectDes)
	mainPath.appendChild(mainTable)
	document.body.appendChild(mainPath)
}

function requestPathFromServer(ProductInfo){
	$.post("http://192.168.43.29:8080/dst/getPath", {suffixId: ProductInfo.id},
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status)
		createPathTable(data, ProductInfo)
	});
}

function sendDistributorInfoToServer(ProductInfo){
	$.post("http://192.168.43.29:8080/dst/setDistributor", {suffixId: ProductInfo.id, companyId: gPrefficsInfo.company.id},
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status);
	});
}

function sendNewDistributorMes(){
	getProductInfoSD(document.getElementById('ndSuffixName').value)
}

function showFormsForUsers(){
	document.getElementById("formGetPath").style.display = 'inline'
	document.getElementById("setNewDistributor").style.display = 'inline'
}
