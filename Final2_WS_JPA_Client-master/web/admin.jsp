<%-- 
    Document   : admin
    Created on : Jun 26, 2020, 2:45:03 PM
    Author     : Admin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <script type="text/javascript">
            var count = 0;
            var cells = [];
            var new_XMLDOM = null;
            var xmlHttp;
            var xmlDOM = null;
            var tmpTableName;
            function addRow(tableId, cells) {
                var tableElement = document.getElementById(tableId);
                var newRow = tableElement.insertRow(tableElement.rows.length);
                var newCell;
                for (var i = 0; i < cells.length; i++) {
                    newCell = newRow.insertCell(newRow.cells.length);
                    newCell.innerHTML = cells[i];
                }
                return newRow;
            }
            function deleteRow(tableId, rowNumber) {
                var tableElement = document.getElementById(tableId);
                if (rowNumber > 0 & rowNumber < tableElement.rows.length) {
                    tableElement.deleteRow(rowNumber);
                } else {
                    alert("Failed");
                }

            }
            function getXmlHttpObject() {
                var xmlHttp = null;
                try {// firefox, opera 8.0+, safari
                    xmlHttp = new XMLHttpRequest();
                } catch (e) {
                    try {// IE
                        xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
                    } catch (e) {
                        xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
                    }
                }
                return xmlHttp;
            }
            function searchNode(node,tableName){
                if(node == null){
                    return;
                }
                if(node.tagName == "fullname"){
                    count++;
                    cells[0] = count;
                    cells[2] = node.firstChild.nodeValue;
                    var role = node.nextSibling.nextSibling;
                    cells[3] = role.firstChild.nodeValue;
                    var user = role.nextSibling;
                    cells[1] user = user.firstChild.nodeValue;
                    addRow(tableName, cells);
                }
                var child = node.childNodes;
                for(var i= 0;i<child.length;i++){
                    searchNode(child[i],tableName);
                }
            }
            function traversalDOMTree(tableName){
                var tableElement = document.getElementById(tableName);
                var i = 1;
                while(i < tableElement.rows.length){
                    deleteRow(tableElement,i);
                }
                count = 0;
                tmpTableName = tableName;
                update();
            }
            function update(){
                xmlHttp = getXmlHttpObject();
                if(xmlHttp == null){
                    alert("Your Browser not support AJAX");
                    return;
                }
                var url = "GetAllController";
                xmlHttp.onreadystatechange = handleStateChange();
                xmlHttp.open("POST",url,true);
                xmlHttp.send(null);
            }
            function handleStateChange(){
                if(xmlHttp.readyState == 4 && xmlHttp.status == 200){
                    var tmp = xmlHttp.responseText;
                    alert(tmp);
                    var parser = new DOMParser();
                    xmlDOM = parser.parseFromString(tmp,"application/xml");
                    searchNode(xmlDOM, tableName);
                }
            }
        </script>
    </head>
    <body>
        <h1>Admin Page</h1>
        <form name="myForm">
            <input type="button" value="GetAll" onclick="traversalDOMTree('dataTable')"/>
        </form>
        <table border ="1" id="dataTable">
            <tr>
                <th>No</th>
                <th>Username</th>
                <th>Fullname</th>
                <th>Role</th>
            </tr>
        </table>
    </body>
</html>
