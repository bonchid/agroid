var gUserId = 0;
var gPrefficsInfo = {};
var gFactorysList = [];
var gProductsList = [];
var qrcode = {};

function startJS(){
	qrcode = new QRCode(document.getElementById("qrcode"), {
		width : 300,
		height : 300
	});
}
function sendAuthData() {
	requestUserId(document.getElementById("login").value, document.getElementById("password").value);
}	

function requestUserId(vLogin, vPassword){
	var vUserId = 0;
	$.post("http://192.168.43.29:8080/auth/getUserId", { login: vLogin, password: vPassword },
	function(data, status){
		if(status == 'success') vUserId = data;
		document.getElementById('authWindow').style.display = "none";
		gUserId = vUserId;
		getPreffix(gUserId);
	});
}

function getPreffix(UserId){
	$.post("http://192.168.43.29:8080/get/preffixInfo", { userId: UserId },
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status);
		gPrefficsInfo = data;
		getFactoryList(gPrefficsInfo.company['id'])
	});
}

function getFactoryList(CompanyId){
	$.post("http://192.168.43.29:8080/get/factoryList", { companyId: CompanyId },
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status);
		gFactorysList = data;
		getProductsList(gPrefficsInfo.company['id'])
	});
}

function getProductsList(CompanyId){
	$.post("http://192.168.43.29:8080/get/productList", { companyId: CompanyId },
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status);
		gProductsList = data;
		showDataToUser()
	});
}

function createProductList(){
	var name = 'ProductList';
    var successTab = document.getElementById('successAuthTab');

    var tr = document.createElement('tr');
    var td = document.createElement('td');
		
	var selectDes= document.createElement('label');
	selectDes.setAttribute('for', 'd' + name);
	selectDes.setAttribute('id', 'l' + name);
	selectDes.innerHTML = 'Продукт: ';
	
	var selectNew = document.createElement('select');
	selectNew.setAttribute('id', 'd' + name);
	
	for(var ix in gProductsList){
		var selectElement = document.createElement('option');
		selectElement.setAttribute('id', 'product' + gProductsList[ix].id);
		selectElement.setAttribute('value', gProductsList[ix].productName);
		selectElement.setAttribute('idValue', gProductsList[ix].id);
		selectElement.innerHTML = gProductsList[ix].productName;
		selectNew.appendChild(selectElement);
	}

    td.appendChild(selectDes);
    td.appendChild(selectNew);

    tr.appendChild(td);

    successTab.appendChild(tr);
}

function createFactoryList(){
	var name = 'FactoryList';
    var successTab = document.getElementById('successAuthTab');

    var tr = document.createElement('tr');
    var td = document.createElement('td');

	var selectDes= document.createElement('label');
	selectDes.setAttribute('for', 'd' + name);
	selectDes.setAttribute('id', 'l' + name);
	selectDes.innerHTML = 'Адрес производства: ';
		
	var selectNew = document.createElement('select');
	selectNew.setAttribute('id', 'd' + name);
		
	for(var ix in gFactorysList){
		var selectElement = document.createElement('option');
		selectElement.setAttribute('id', 'factory' + gFactorysList[ix].id);
		selectElement.setAttribute('value', gFactorysList[ix].address);
		selectElement.setAttribute('idValue', gFactorysList[ix].id);
		selectElement.innerHTML = gFactorysList[ix].address;
		selectNew.appendChild(selectElement)
	}
		
	td.appendChild(selectDes);
	td.appendChild(selectNew);

    tr.appendChild(td);

    successTab.appendChild(tr);
}

function createSendButton(){
	var name = 'SendInfoButton';
	var successTab = document.getElementById('successAuthTab');

    var tr = document.createElement('tr');
    var td = document.createElement('td');
	td.setAttribute('align', 'center');

	var selectNew = document.createElement('button');
	selectNew.setAttribute('id', name);
	selectNew.setAttribute('onclick', 'sendProductInformation()');
	selectNew.innerHTML = 'Отправить';

    td.appendChild(selectNew);
    tr.appendChild(td);
	successTab.appendChild(tr);
}

function sendProductInformation(){
	var vPreffixId = gPrefficsInfo.id;
	var vProductId = document.getElementById('dProductList')
        .options[document.getElementById('dProductList').selectedIndex].getAttribute('idValue');
	var vFactoryId = document.getElementById('dFactoryList')
        .options[document.getElementById('dFactoryList').selectedIndex].getAttribute('idValue');
	var vSuffix = document.getElementById('iSuffix').value;
	var vManufactureDate = document.getElementById('iManufactureDate').value;
		
	$.post("http://192.168.43.29:8080/set/newSuffix", { suffix: vSuffix, preffixId: vPreffixId,
            productId: vProductId, manufactureDate: vManufactureDate, factoryId: vFactoryId},
	function(data, status){
		console.log("Data: " + JSON.stringify(data) + "\nStatus: " + status);
		createQrCode()
	});
}

function createQrCode(){
	document.getElementById("qrcode").style.display = "inline";
	qrcode.makeCode(gPrefficsInfo.registry + "." +
        gPrefficsInfo.registrant + "\\" +  document.getElementById('iSuffix').value);
}

function showDataToUser(){
    document.getElementById('h3').style.display = "inline";
	document.getElementById('lPrefficsInfo').style.display = "inline";
	document.getElementById('dPrefficsInfo').innerHTML = gPrefficsInfo.registry + "." + gPrefficsInfo.registrant;
	document.getElementById('lCompanyName').style.display = "inline";
	document.getElementById('dCompanyName').innerHTML = gPrefficsInfo.company.name;
	document.getElementById('inputInv').style.display = "inline";
    document.getElementById('inputInv2').style.display = "inline";
	createProductList();
	createFactoryList();
	createSendButton();
}
